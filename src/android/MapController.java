package com.dagatsoin.plugins.mapbox;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;

import android.util.Base64;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.vietmap.android.gestures.MoveGestureDetector;
import vn.vietmap.android.gestures.RotateGestureDetector;
import vn.vietmap.android.gestures.StandardScaleGestureDetector;
import vn.vietmap.vietmapsdk.camera.CameraPosition;
import vn.vietmap.vietmapsdk.camera.CameraUpdateFactory;
import vn.vietmap.vietmapsdk.geometry.LatLng;
import vn.vietmap.vietmapsdk.geometry.LatLngBounds;
import vn.vietmap.vietmapsdk.maps.MapView;
import vn.vietmap.vietmapsdk.maps.Style;
import vn.vietmap.vietmapsdk.maps.VietMapGL;
import vn.vietmap.vietmapsdk.maps.VietMapGLOptions;
import vn.vietmap.vietmapsdk.style.expressions.Expression;
import vn.vietmap.vietmapsdk.style.layers.CircleLayer;
import vn.vietmap.vietmapsdk.style.layers.Layer;
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory;
import vn.vietmap.vietmapsdk.style.layers.SymbolLayer;
import vn.vietmap.vietmapsdk.style.sources.CannotAddSourceException;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonOptions;
import vn.vietmap.vietmapsdk.style.sources.GeoJsonSource;

class MapController implements VietMapGL.OnMapClickListener {
    @Nullable
    private String mSelectableFeaturePropType;
    @Nullable private String mSelectedFeatureLayerId;
    @Nullable private String mSelectedFeatureSourceId;
    private Style style;

    private MapView mMapView;
    private String mStyleUrl;
    private VietMapGL vietMapGL;
    private Activity mActivity;
    boolean isReady = false;
    Runnable mapReady;
    private final FeatureCollection mSelectedFeatureCollection =  FeatureCollection.fromFeatures(new ArrayList<>());
    private boolean mHasSelectedFeature;

    MapView getMapView() {
        return mMapView;
    }

    String getSelecteFeatureCollection() {
        return mSelectedFeatureCollection.toJson();
    }

    MapController(
            final JSONObject options,
            Activity activity,
            @Nullable String selectedFeatureLayerId,
            @Nullable String selectedFeatureSourceId,
            @Nullable String selectableFeaturePropType,
            @Nullable final ScrollView scrollView
    ) {

        VietMapGLOptions initOptions;
        try {
            initOptions = createMapboxMapOptions(options);
            mStyleUrl = getStyle(options.getString("style"));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        mActivity = activity;

        mMapView = new MapView(mActivity, initOptions);
        mMapView.setLayoutParams(
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                ));

        // Prevent scroll to intercept the touch when pane the map
        if (scrollView != null) {
            mMapView.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return mMapView.onTouchEvent(event);
            });
        }

        // Important. As we do not use the activity creation as stated in the Mapbox doc,
        // we need to call manually the different life cycle.
        // Otherwise the map won't display entirely.
        mMapView.onCreate(null);
        mMapView.onStart();
        mMapView.onResume();

        mMapView.getMapAsync(mapView -> {
            vietMapGL = mapView;
            mSelectedFeatureSourceId = selectedFeatureSourceId;
            mSelectedFeatureLayerId = selectedFeatureLayerId;
            mSelectableFeaturePropType= selectableFeaturePropType;
            vietMapGL.addOnMapClickListener(MapController.this);

            mapView.setStyle(new Style.Builder().fromUrl(mStyleUrl), _style -> {
                style = _style;
                isReady = true;
                mapReady.run();
            });
        });

    }

    OfflineController getOfflineController() {
        @Nullable OfflineController offlineController = OfflineControllerPool.get(mStyleUrl);
        return offlineController != null ? offlineController : OfflineControllerPool.create(mActivity, mStyleUrl);
    }

    void addFeatureCollection(String featureCollectionId, FeatureCollection featureCollection, boolean isClusterEnabled, Integer clusterMaxZoom, Integer clusterRadius ) {
        final GeoJsonSource geoJsonSource = new GeoJsonSource(featureCollectionId, featureCollection, new GeoJsonOptions()
                .withCluster(isClusterEnabled)
                .withClusterMaxZoom(clusterMaxZoom)
                .withClusterRadius(clusterRadius)
        );
        if (style.getSource(featureCollectionId) == null) {
            addGeoJsonSource(geoJsonSource);
        }
    }

    void addFeature(String featureId, Feature feature ) {
        final GeoJsonSource geoJsonSource = new GeoJsonSource(featureId, feature);
        if (style.getSource(featureId) == null) {
            addGeoJsonSource(geoJsonSource);
        }
    }

    void addGeoJsonSource(String sourceId) {
        if (style.getSource(sourceId) == null) {
            style.addSource(new GeoJsonSource(sourceId));
        }
    }

    private void addGeoJsonSource(GeoJsonSource geoJsonSource) {
        if (style.getSource(geoJsonSource.getId()) == null) {
            // Throw when a source exists with the same id
            style.addSource(geoJsonSource);
        }
    }

    boolean removeSource(String sourceId) {
        try {
            // Throw when source is still in use
            return style.removeSource(sourceId);
        } catch (CannotAddSourceException e) {
            e.printStackTrace();
            return false;
        }
    }

    void setSourceGeoJsonData(String sourceId, FeatureCollection featureCollection) {
        final GeoJsonSource source = style.getSourceAs(sourceId);
        if (source != null) {
            // https://github.com/mapbox/mapbox-gl-native/issues/14565#issuecomment-496923239
            source.setGeoJson(FeatureCollection.fromFeatures(new ArrayList<>(Objects.requireNonNull(featureCollection.features()))));
        }
    }

    void setSourceGeoJsonData(String sourceId, Feature feature) {
        final GeoJsonSource source = style.getSourceAs(sourceId);
        if (source != null) {
            source.setGeoJson(feature);
        }
    }

    void addSymbolLayer(
            String layerId,
            String sourceId,
            Integer minZoom,
            Integer maxZoom,
            @Nullable Expression filter,
            @Nullable String beforeId
    ) {
        if (style.getLayer(layerId) != null) return;

        final SymbolLayer symbolLayer = new SymbolLayer(layerId, sourceId);
        symbolLayer.setMinZoom(minZoom);
        symbolLayer.setMaxZoom(maxZoom);
        if (filter != null) {
            symbolLayer.setFilter(filter);
        }


        if (beforeId == null || beforeId.isEmpty()) {
            style.addLayer(symbolLayer);
        } else {
            if (style.getLayer(layerId) != null) {
                removeLayer(layerId);
            }
            style.addLayerBelow(symbolLayer, beforeId);
        }
    }

    void addCircleLayer(
            String layerId,
            String sourceId,
            Integer minZoom,
            Integer maxZoom,
            @Nullable Expression filter,
            @Nullable String beforeId
    ) {
        if (style.getLayer(layerId) != null) return;

        CircleLayer circles = new CircleLayer(layerId, sourceId);

        circles.setMinZoom(minZoom);
        circles.setMaxZoom(maxZoom);

        if (filter != null) {
            circles.setFilter(filter);
        }


        if (beforeId == null || beforeId.isEmpty()) {
            style.addLayer(circles);
        } else {
            if (style.getLayer(layerId) != null) {
                removeLayer(layerId);
            }
            style.addLayerBelow(circles, beforeId);
        }
    }

    boolean removeLayer(String layerId) {
        return style.removeLayer(layerId);
    }

    void addImage(String imageId, JSONObject jsonImage) {
        try {
            final Bitmap bitmap = createImage(jsonImage);
            style.addImage(imageId, bitmap);
        } catch (JSONException | IOException | SVGParseException e) {
            e.printStackTrace();
        }
    }

    void removeImage(String imageId) {
        style.removeImage(imageId);
    }


    void setLayoutPropertyIconImage(String layerId, String imageId) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            layer.setProperties(PropertyFactory.iconImage(imageId));
        }
    }

    void setLayoutPropertyOffset(String layerId, Float[] offset) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            layer.setProperties(PropertyFactory.iconOffset(offset));
        }
    }

    void setLayoutPropertySize(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.iconSize(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // Or just a float
                layer.setProperties(PropertyFactory.iconSize(Float.valueOf(value)));
            }
        }
    }

    void setLayoutPropertyIconOverlap(String layerId, boolean isOverlap) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            layer.setProperties(PropertyFactory.iconAllowOverlap(isOverlap));
        }
    }

    void setLayoutPropertyTextField(String layerId, String fieldId) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            layer.setProperties(PropertyFactory.textField(fieldId));
        }
    }

    void setLayoutPropertyTextSize(String layerId, String size) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Try to parse expression
                layer.setProperties(PropertyFactory.textSize(Expression.Converter.convert(size)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // It is a float
                layer.setProperties(PropertyFactory.textSize(Float.valueOf(size)));
            }
        }
    }

    void setLayoutPropertyTextFont(String layerId, String font) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Try to parse expression
                layer.setProperties(PropertyFactory.textFont(Expression.Converter.convert(font)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // It was an array of fonts
                final String s = font.substring(1, font.length() -1 );
                layer.setProperties(PropertyFactory.textFont(s.split(",")));
            }
        }
    }

    void setPaintPropertyCircleColor(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);

        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.circleColor(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // Or just a string
                layer.setProperties(PropertyFactory.circleColor(value));
            }
        }
    }

    void setPaintPropertyCircleRadius(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.circleRadius(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // Or just a float
                layer.setProperties(PropertyFactory.circleRadius(Float.valueOf(value)));
            }
        }
    }

    void setPaintPropertyTextColor(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);

        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.textColor(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // Or just a string
                layer.setProperties(PropertyFactory.textColor(value));
            }
        }
    }

    void setPaintPropertyTextHaloBlur(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.textHaloBlur(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // Or just a float
                layer.setProperties(PropertyFactory.textHaloBlur(Float.valueOf(value)));
            }
        }
    }

    void setPaintPropertyTextHaloColor(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.textHaloColor(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException|NullPointerException e) {
                // Or just a string
                layer.setProperties(PropertyFactory.textHaloColor(value));
            }
        }
    }

    void setPaintPropertyTextHaloWidth(String layerId, String value) {
        final Layer layer = style.getLayer(layerId);
        if (layer != null) {
            try {
                // Could be an array expression
                layer.setProperties(PropertyFactory.textHaloWidth(Expression.Converter.convert(value)));
            } catch (JsonSyntaxException |NullPointerException e) {
                // Or just a float
                layer.setProperties(PropertyFactory.textHaloWidth(Float.valueOf(value)));
            }
        }
    }

    private final float retinaFactor = Resources.getSystem().getDisplayMetrics().density;

    private int applyRetinaFactor(long d) {
        return Math.round(d * retinaFactor);
    }

    private BitmapDrawable createSVG(SVG svg, int width, int height) {
        if (width == 0)
            width = applyRetinaFactor((int) Math.ceil(svg.getDocumentWidth()));
        if (height == 0)
            height = applyRetinaFactor((int) Math.ceil(svg.getDocumentHeight()));
        Bitmap newBM = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bmCanvas = new Canvas(newBM);
        svg.renderToCanvas(bmCanvas);
        return new BitmapDrawable(mActivity.getApplicationContext().getResources(), newBM);
    }

    /**
     * Creates icon for symbol from url or local file
     *
     * @param imageObject The properties.image part of a JSON feature
     * @return an icon with a custom image
     */
    // Thanks @anothar
    private Bitmap createImage(JSONObject imageObject) throws JSONException, IOException, SVGParseException {
        InputStream stream = null;
        BitmapDrawable bitmapDrawable;
        Bitmap bitmap = null;
        Context ctx = mActivity.getApplicationContext();
        AssetManager am = ctx.getResources().getAssets();

        try {
            if (imageObject != null) {
                if (imageObject.has("path")) {
                    String fileLocation = imageObject.getString("path");

                    if (fileLocation == null) {
                        throw new Error("Need a file name");
                    }

                    final File iconFile = new File(mActivity.getFilesDir(), fileLocation);

                    if (iconFile.exists()) {
                        stream = new FileInputStream(iconFile);
                    }
                    else {
                        try {
                            stream = am.open(fileLocation);
                        } catch (IOException e) {
                            throw new IOException("File does not exists in assets folder or application folder: " + fileLocation);

                        }
                    }

                    if (fileLocation.endsWith(".svg")) {
                        bitmapDrawable = createSVG(SVG.getFromInputStream(stream), imageObject.has("width") ? applyRetinaFactor(imageObject.getInt("width")) : 0,
                                imageObject.has("height") ? applyRetinaFactor(imageObject.getInt("height")) : 0);
                    } else {
                        bitmapDrawable = new BitmapDrawable(ctx.getResources(), stream);
                    }
                } else if (imageObject.has("data")) {
                    byte[] decodedBytes = Base64.decode(imageObject.getString("data"), 0);
                    bitmapDrawable = new BitmapDrawable(ctx.getResources(), BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length));

                } else if (imageObject.has("svg")) {
                    bitmapDrawable = createSVG(SVG.getFromString(imageObject.getString("svg")), imageObject.has("width") ? applyRetinaFactor(imageObject.getInt("width")) : 0,
                            imageObject.has("height") ? applyRetinaFactor(imageObject.getInt("height")) : 0);
                } else {
                    throw new JSONException("Not found image data");
                }
                if (imageObject.has("width") && imageObject.has("height")) {
                    bitmap = new BitmapDrawable(ctx.getResources(),
                            Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(),
                                    applyRetinaFactor(imageObject.getInt("width")),
                                    applyRetinaFactor(imageObject.getInt("height")),
                                    true
                            )).getBitmap();
                } else {
                    bitmap = bitmapDrawable.getBitmap();
                }
            }
        } finally {
            if (stream != null)
                stream.close();
        }
        return bitmap;
    }

    public LatLng getCenter() {
        CameraPosition cameraPosition = vietMapGL.getCameraPosition();
        double lat = cameraPosition.target.getLatitude();
        double lng = cameraPosition.target.getLongitude();
        return new LatLng(lat, lng);
    }

    public void setCenter(double... coords) {
        CameraPosition cameraPosition = vietMapGL.getCameraPosition();
        double lng = coords.length > 0
                ? coords[0]
                : cameraPosition.target != null
                ? cameraPosition.target.getLongitude()
                : 0;
        double lat = coords.length > 1
                ? coords[1]
                : cameraPosition.target != null
                ? cameraPosition.target.getLatitude()
                : 0;
        double alt = coords.length > 2
                ? coords[2]
                : cameraPosition.target != null
                ? cameraPosition.target.getAltitude()
                : 1000;

        vietMapGL.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(lat, lng, alt))
                        .build()
        ));
    }

    void scrollMap(float x, float y) {
        vietMapGL.scrollBy(x, y);
    }

    double getTilt() {
        return vietMapGL.getCameraPosition().tilt;
    }

    void setTilt(double tilt) {
        vietMapGL.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .tilt(tilt)
                        .build()
        ));
    }

    void flyTo(JSONObject position) {
        CameraPosition cameraPosition = vietMapGL.getCameraPosition();

        try {
            int duration = position.isNull("duration") ? 5000 : position.getInt("duration");

            vietMapGL.animateCamera(CameraUpdateFactory.newCameraPosition(MapController.getCameraPosition(position, cameraPosition)), duration);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void addMapClickCallback(Runnable callback) {
        if (!isReady) return;
        vietMapGL.addOnMapClickListener(new MapClickListener(callback));
    }

    public double getZoom() {
        return vietMapGL.getCameraPosition().zoom;
    }

    public void setZoom(double zoom) {
        CameraPosition position = new CameraPosition.Builder()
                .zoom(zoom)
                .build();

        vietMapGL.moveCamera(CameraUpdateFactory
                .newCameraPosition(position));
    }

    void zoomTo(double zoom) {
        CameraPosition position = new CameraPosition.Builder()
                .zoom(zoom)
                .build();

        vietMapGL.animateCamera(CameraUpdateFactory
                .newCameraPosition(position));
    }

    LatLngBounds getBounds() {
        return vietMapGL.getProjection().getVisibleRegion().latLngBounds;
    }

    private static class JSONLatLngBounds {
        final JSONArray sw = new JSONArray();
        final JSONArray ne = new JSONArray();
        JSONLatLngBounds(LatLngBounds latLngBounds) throws JSONException {
            sw.put(latLngBounds.getSouthWest().getLongitude());
            sw.put(latLngBounds.getSouthWest().getLatitude());
            ne.put(latLngBounds.getNorthEast().getLongitude());
            ne.put(latLngBounds.getNorthEast().getLatitude());
        }
    }

    JSONLatLngBounds getJSONBounds(LatLngBounds latLngBounds) throws JSONException {
        return new JSONLatLngBounds(latLngBounds);
    }

    PointF convertCoordinates(LatLng coords) {
        return vietMapGL.getProjection().toScreenLocation(coords);
    }

    LatLng convertPoint(PointF point) {
        return vietMapGL.getProjection().fromScreenLocation(point);
    }

    private VietMapGLOptions createMapboxMapOptions(JSONObject options) throws JSONException {
        VietMapGLOptions opts = new VietMapGLOptions();
        opts.attributionEnabled(options.isNull("hideAttribution") || !options.getBoolean("hideAttribution"));
        opts.logoEnabled(options.isNull("hideLogo") || options.getBoolean("hideLogo"));
        opts.camera(MapController.getCameraPosition(options.isNull("cameraPosition") ? null : options.getJSONObject("cameraPosition"), null));
        opts.compassEnabled(options.isNull("hideCompass") || !options.getBoolean("hideCompass"));
        opts.rotateGesturesEnabled(options.isNull("disableRotation") || !options.getBoolean("disableRotation"));
        opts.scrollGesturesEnabled(options.isNull("disableScroll") || !options.getBoolean("disableScroll"));
        opts.zoomGesturesEnabled(options.isNull("disableZoom") || !options.getBoolean("disableZoom"));
        opts.tiltGesturesEnabled(options.isNull("disableTilt") || !options.getBoolean("disableTilt"));
        opts.attributionMargins((!options.isNull("hideAttribution") && options.getBoolean("hideAttribution")) ? new int[]{-300, 0, 0, 0} : null);
        opts.logoMargins((!options.isNull("hideLogo") && options.getBoolean("hideLogo")) ? new int[]{-300, 0, 0, 0} : null);
        return opts;
    }

    private static String getStyle(final String requested) {
        return requested;
    }

    private static CameraPosition getCameraPosition(JSONObject position, @Nullable CameraPosition start) throws JSONException {
        CameraPosition.Builder builder = new CameraPosition.Builder(start);

        if (position != null) {
            if (!position.isNull("target")) {
                JSONObject target = position.getJSONObject("target");
                builder.target(new LatLng(target.getDouble("lat"), target.getDouble("lng")));
            }

            if (!position.isNull("zoom")) {
                builder.zoom(position.getDouble("zoom"));
            }

            if (!position.isNull("bearing")) {
                builder.bearing(position.getDouble("bearing"));
            }

            if (!position.isNull("tilt")) {
                builder.tilt(position.getDouble("tilt"));
            }
        }
        return builder.build();
    }

    JSONObject getJSONCameraScreenPosition() throws JSONException {
        CameraPosition position = vietMapGL.getCameraPosition();
        PointF screenPosition = convertCoordinates(position.target);
        try {
            return new JSONObject()
                    .put("x", screenPosition.x)
                    .put("y", screenPosition.y)
                    .put("alt", position.target.getAltitude())
                    .put("tilt", position.tilt)
                    .put("bearing", position.bearing);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONException(e.getMessage());
        }
    }

    JSONObject getJSONCameraGeoPosition() throws JSONException {
        CameraPosition position = vietMapGL.getCameraPosition();

        try {
            return new JSONObject()
                    .put("zoom", position.zoom)
                    .put("long", position.target != null ? position.target.getLongitude() : 0)
                    .put("alt", position.target != null ? position.target.getAltitude() : 0)
                    .put("tilt", position.tilt)
                    .put("bearing", position.bearing);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONException(e.getMessage());
        }
    }

    void addOnWillStartLoadingMapListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnWillStartLoadingMapListener(() -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnWillStartRenderingMapListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnWillStartRenderingMapListener(() -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnCameraWillChangeListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnCameraWillChangeListener((boolean isAnimated) -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnCameraDidChangeListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnCameraDidChangeListener((boolean isAnimated) -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnDidFinishLoadingStyleListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnDidFinishLoadingStyleListener(() -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnSourceChangedListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnSourceChangedListener((String id) -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnWillStartRenderingFrameListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnWillStartRenderingFrameListener(() -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnDidFinishRenderingFrameListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnDidFinishRenderingFrameListener((boolean fully) -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnDidFinishLoadingMapListener(RunnableWithArg<JSONObject> callback) {
        mMapView.addOnDidFinishLoadingMapListener(() -> {
            try {
                callback.run(this.getJSONCameraGeoPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    void addOnDidFinishRenderingMapListener(RunnableWithArg<Boolean> callback) {
        mMapView.addOnDidFinishRenderingMapListener(callback::run);
    }

    void addOnMoveListener(RunnableWithArg<MapEventPayload> callback) {
        vietMapGL.addOnMoveListener(new VietMapGL.OnMoveListener() {
            @Override
            public void onMoveBegin(@NonNull MoveGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnMoveStart, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMove(@NonNull MoveGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnMove, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMoveEnd(@NonNull MoveGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnMoveEnd, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void addOnFlingListener(RunnableWithArg<MapEventPayload> callback) {
        vietMapGL.addOnFlingListener(() -> {
            try {
                callback.run(new MapEventPayload(MapEventType.OnFling, getBounds()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
    void addOnRotateListener(RunnableWithArg<MapEventPayload> callback) {
        vietMapGL.addOnRotateListener(new VietMapGL.OnRotateListener() {
            @Override
            public void onRotateBegin(@NonNull RotateGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnRotateStart, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRotate(@NonNull RotateGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnRotate, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRotateEnd(@NonNull RotateGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnRotateEnd, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void addOnScaleListener(RunnableWithArg<MapEventPayload> callback) {
        vietMapGL.addOnScaleListener(new VietMapGL.OnScaleListener() {
            @Override
            public void onScaleBegin(@NonNull StandardScaleGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnScaleStart, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onScale(@NonNull StandardScaleGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnScale, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onScaleEnd(@NonNull StandardScaleGestureDetector detector) {
                try {
                    callback.run(new MapEventPayload(MapEventType.OnScaleEnd, getBounds()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if (style != null && mSelectedFeatureLayerId != null) {

            final PointF pixel = convertCoordinates(point);
            List<Feature> features = vietMapGL.queryRenderedFeatures(pixel);
            Feature feature = null;
            for (int i = 0; i < features.size(); i++ ) {
                final JsonObject properties = features.get(i).properties();
                if (properties != null && properties.has("type") && properties.get("type").getAsString().equals(mSelectableFeaturePropType)) {
                    feature = features.get(i);
                    break;
                }
            }
            List<Feature> selectedFeature = vietMapGL.queryRenderedFeatures(pixel, mSelectedFeatureLayerId);

            if (selectedFeature.size() > 0 && mHasSelectedFeature) {
                return false;
            }

            if (feature == null) {
                if (mHasSelectedFeature) {
                    deselectFeature();
                }
                return false;
            }

            if (mHasSelectedFeature) {
                deselectFeature();
            }

            selectFeature(feature);
        }
        return false;
    }

    private final TimeInterpolator interpolator = new BounceInterpolator();

    private void selectFeature(final Feature feature) {
        if (mSelectedFeatureLayerId == null || mSelectedFeatureSourceId == null) return;

        final SymbolLayer selectedFeatureLayer = (SymbolLayer) style.getLayer(mSelectedFeatureLayerId);

        if (selectedFeatureLayer == null) return;

        GeoJsonSource source = style.getSourceAs(mSelectedFeatureSourceId);
        if (source != null) {
            source.setGeoJson(feature);
        }
        ValueAnimator featureAnimator = new ValueAnimator();
        featureAnimator.setObjectValues(0f, 1f);
        featureAnimator.setDuration(800);
        featureAnimator.setInterpolator(interpolator);
        featureAnimator.addUpdateListener(animator -> {
            final float factor = (float)(animator.getAnimatedValue());
            selectedFeatureLayer.setProperties(PropertyFactory.iconSize((float) (1 + .25 * factor)));
        });
        featureAnimator.start();
        mHasSelectedFeature = true;
    }

    void deselectFeature() {
        if (mSelectedFeatureSourceId == null) return;

        final GeoJsonSource source = style.getSourceAs(mSelectedFeatureSourceId);
        if (source != null) {
            source.setGeoJson(FeatureCollection.fromFeatures(
                    new Feature[]{}));
        }
        mHasSelectedFeature = false;
    }

    public enum MapEventType {
        OnMoveStart("OnMoveStart"),
        OnMove("OnMove"),
        OnMoveEnd("OnMoveEnd"),
        OnRotateStart("OnRotateStart"),
        OnRotate("OnRotate"),
        OnRotateEnd("OnRotateEnd"),
        OnScaleStart("OnScaleStart"),
        OnScale("OnScale"),
        OnScaleEnd("OnScaleEnd"),
        OnFling("OnFling");

        private final String eventType;

        MapEventType(String type) {
            this.eventType = type;
        }

        public String getType() {
            return eventType;
        }
    }

    public class MapEventPayload {
        final public MapEventType type;
        final public JSONLatLngBounds latLngBounds;

        public MapEventPayload(MapEventType _mapEventType, LatLngBounds _latLngBounds) throws JSONException {
            type = _mapEventType;
            latLngBounds = getJSONBounds(_latLngBounds);
        }

        public JSONObject toJSON() throws JSONException {
            JSONObject payload = new JSONObject();
            payload.put("type", this.type.eventType);
            payload.put("latLngBounds", this.latLngBounds);
            return payload;
        }
    }

    private class MapClickListener implements VietMapGL.OnMapClickListener {
        private final Runnable callback;

        MapClickListener(Runnable cb) {
            callback = cb;
        }

        @Override
        public boolean onMapClick(@NonNull LatLng point) {
            List<Feature> features = vietMapGL.queryRenderedFeatures(convertCoordinates(point));
            Objects.requireNonNull(mSelectedFeatureCollection.features()).clear();
            Objects.requireNonNull(mSelectedFeatureCollection.features()).addAll(features);
            callback.run();
            return true;
        }
    }
}