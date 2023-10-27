/**
 * This file export the plugin interface for Cordova
 * It will be accessible through `window.Vietmap` or directly `Vietmap`
 * It also exports the types of arguments used in the API for development purpose.
 */
export declare enum MapEventType {
    OnMoveStart = "OnMoveStart",
    OnMove = "OnMove",
    OnMoveEnd = "OnMoveEnd",
    OnRotateStart = "OnRotateStart",
    OnRotate = "OnRotate",
    OnRotateEnd = "OnRotateEnd",
    OnScaleStart = "OnScaleStart",
    OnScale = "OnScale",
    OnScaleEnd = "OnScaleEnd",
    OnFling = "OnFling"
}
export declare type MapEventPayload = {
    type: MapEventType;
    latLngBounds: Bounds;
};
declare type PickOne<T> = {
    [P in keyof T]: Record<P, T[P]> & Partial<Record<Exclude<keyof T, P>, undefined>>;
}[keyof T];
export declare type LayoutProperties = {
    'icon-image': string;
    'icon-offset': [number, number];
    'icon-size': number | Expression;
    'icon-allow-overlap': boolean;
    'text-size': number | Expression;
    'text-field': string;
    'text-font': string[];
};
export declare type LayoutProperty = PickOne<LayoutProperties>;
export declare type PaintProperties = {
    'text-color': string | Expression;
    'text-halo-blur': number | Expression;
    'text-halo-color': string | Expression;
    'text-halo-width': number | Expression;
};
export declare type MapEventListener = (payload: MapEventPayload) => void;
export declare type Bounds = {
    sw: [number, number];
    ne: [number, number];
};
export declare type LayerType = 'symbol';
export declare type Expression = Array<string | boolean | number | Expression>;
export declare type DownloadState = {
    regionName: string;
    isComplete: boolean;
    requiredResourceCount: number;
    downloadState: 'STATE_ACTIVE' | 'STATE_INACTIVE';
    completeTileSize: number;
    completeTileCount: number;
    completeResourceSize: number;
    completedResourceCount: number;
};
export declare type Layer = {
    id: string;
    source: string;
    sourcelayer?: string;
    type?: LayerType;
    minzoom?: number;
    filter?: Expression;
    maxzoom?: number;
    layout?: Partial<LayoutProperties>;
    paint?: Partial<PaintProperties>;
};
export declare type CameraOptions = {
    center: LngLat;
    zoom: number;
    bearing: number;
    pitch: number;
    around: LngLat;
};
export declare type CameraPosition = {
    target: Coords;
    zoom: number;
    bearing: number;
    tilt: number;
};
export declare type SourceData<T extends GeoJSON.GeometryObject, P> = {} | GeoJSON.FeatureCollection<T, P> | GeoJSON.Feature<T, P>;
export declare type Source<T extends GeoJSON.GeometryObject, P> = {
    type: 'geojson';
    data: SourceData<T, P>;
    cluster?: boolean;
    clusterMaxZoom?: number;
    clusterRadius?: number;
};
export declare type LngLat = [number, number];
export declare type Coords = {
    lng: number;
    lat: number;
};
export declare type ScreenCoords = {
    x: number;
    y: number;
};
/**
 * The properties of an image used by the Vietmap map.
 * The path is relative to the app assets folder.
 * If you want to access an image from the `www` folder
 * you will set the path value as `www/myImage.png`
 */
export declare type ImageProperties = {
    height: number;
    width: number;
    path: string;
};
export declare type MapOptions = {
    domContainer: HTMLElement;
    style: string;
    additionalDomElements?: HTMLElement[];
    cameraPosition?: any;
    selectedFeatureLayerId?: string;
    selectedFeatureSourceId?: string;
    selectableFeaturePropType?: string;
    hideAttribution?: boolean;
    hideLogo?: boolean;
    hideCompass?: boolean;
    disableRotation?: boolean;
    disableScroll?: boolean;
    disableTilt?: boolean;
    disableZoom?: boolean;
    disablePitch?: boolean;
};
export declare type VietmapContainerParams = {
    domContainer: HTMLElement;
    additionalDomElements?: HTMLElement[];
};
export declare type DownloadParams = {
    regionName: string;
    styleUrl?: string;
    bounds: Bounds;
    minZoom: number;
    maxZoom: number;
};
export declare type OfflineRegionParams = {
    styleUrl?: string;
    regionName?: string;
};
export interface Vietmap {
    /**
     * Display the map. Create it if needed.
     * @param options
     * @param successCallback called when the map is displayed
     * @param errorCallback called in case of error
     */
    show(options: MapOptions, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Colorize clickable HTML elements with for debug purpose.
     * @param isDebug
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    setDebug(isDebug: boolean, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Set a map as clickable. If false, the user won't be abble to move the map
     * with gesture.
     * @param isClickable
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    setClickable(isClickable: boolean, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Hide the map. The map is still in memory.
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    hide(successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Destroy a map and free the memory.
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    destroy(successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Set the HTML container of the Map. It will resize
     * the map to fit in the container and update the clickable
     * HTML elements overlaying the map.
     * @param params
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    setContainer(params: VietmapContainerParams, successCallback?: () => void, errorCallback?: (e: string) => void, id?: number): void;
    /**
     * Download a region delimited by [[Bounds]]()
     * @param options
     * @param successCallback a success callback taking a [[DowlloadState]] in argument
     * @param errorCallback called in case of error
     */
    downloadRegion(options: DownloadParams, statusCallback: (state: DownloadState) => void, errorCallback?: (e: string | {
        reason: 'REGION_EXISTS';
    }) => void): void;
    /**
     * Get the availble names of offline region.
     * @param styleUrl the Vietmap style URL
     * @param successCallback a callback taking a string Array of region names.
     * @param errorCallback called in case of error
     */
    getOfflineRegionList(styleUrl?: string, successCallback?: (regionNames: string[]) => void, errorCallback?: (e: string) => void): void;
    /**
     * Delete an offline region
     * @param params
     * @param successCallback a callback taking the result boolean of the deletion
     * @param errorCallback called in case of error
     */
    deleteOfflineRegion(params?: OfflineRegionParams, successCallback?: (isDeleted: boolean) => void, errorCallback?: (e: string) => void): void;
    /**
     * Pause the download of a region
     * @param options
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    pauseDownload(options?: OfflineRegionParams, successCallback?: () => void, errorCallback?: (e: string) => void, id?: number): void;
    /**
     * Resume the paused download of a region
     * @param options
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    resumeDownload(options?: OfflineRegionParams, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Add a map click callback to the native map.
     * @param callback the click callback taking a collection of JSON feature geometry objects.
     * @param errorCallback called in case of error
     */
    addMapClickCallback(callback?: (featureCollection: GeoJSON.FeatureCollection<GeoJSON.GeometryObject>) => void, errorCallback?: (e: string) => void, id?: number): void;
    /**
     * Deselect the current feature of the map.
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    deselect(callback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Add an image to the map style. For instance, a marker image.
     * You must add the image before referencing it in a resource.
     * @param imageId
     * @param image
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    addImage(imageId: string, image: ImageProperties, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Remove an image from the map style.
     * @param imageId
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    removeImage(imageId: string, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Add a layer to the map style
     * @param layer
     * @param beforeId the layer will be added below
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    addLayer(layer: Layer, beforeId?: number, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Set a layout property for a layer
     * @param layerId
     * @param name the property name
     * @param value the property value
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    setLayoutProperty(layerId: string, property: LayoutProperty, successCallback?: () => void, errorCallback?: (_e: string) => void): void;
    /**
     * Remove a layer from the map style
     * @param layerId
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    removeLayer(layerId: string, successCallback?: () => void, errorCallback?: (_e: string) => void): void;
    /**
     * Add a source to the map style
     * @param sourceId
     * @param source
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    addSource(sourceId: string, source: Source<any, any>, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Remove a source from the map style
     * @param sourceId
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    removeSource(sourceId: string, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Set the data of a source. Use this when you want to animate data.
     * @param sourceId
     * @param geoJson
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    setGeoJson(sourceId: string, geoJson: SourceData<any, any>, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Smoothly jump to a position on the map.
     * @param cameraPosition
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    flyTo(cameraPosition: Partial<CameraPosition> & {
        duration: number;
    }, successCallback?: () => void, errorCallback?: (e: string) => void, id?: number): void;
    /**
     * Center the map on another [[LngLat]] coordinates. (not animated)
     * @param center the new coordinautes
     * @param successCallback called when animation start
     * @param errorCallback called in case of error
     */
    setCenter(center: LngLat, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Get the [[Coords]] of the map center.
     * @param resultCallback takes a [[Coords]] argument.
     * @param errorCallback called in case of error
     */
    getCenter(resultCallback: (center: Coords) => void, errorCallback?: (_e: string) => void): void;
    /**
     * Animate a camera translation.
     * @param delta
     * @param successCallback called when animation start
     * @param errorCallback called in case of error
     */
    scrollMap(delta: [number, number], successCallback: (center: Coords) => void, errorCallback?: (e: string) => void): void;
    /**
     * Set the map pitch (not animated)
     * @param pitch
     * @param successCallback called on success
     * @param errorCallback called in case of error
     */
    setPitch(pitch: number, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Get the current map pitch
     * @param resultCallback takes a the current pitch as argument.
     * @param errorCallback called in case of error
     */
    getPitch(resultCallback?: (pitch: number) => void, errorCallback?: (e: string) => void): void;
    /**
     * Set the zoom of the map (not animated)
     * @param zoom
     * @param options
     * @param successCallback
     * @param errorCallback
     * @param id
     */
    setZoom(zoom: number, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Return the current map zoom
     * @param resultCallback takes a the current zoom as argument.
     * @param errorCallback called in case of error
     */
    getZoom(resultCallback?: (zoom: number) => void, errorCallback?: (e: string) => void, id?: number): void;
    /**
     *
     * @param zoom
     * @param successCallback called when animation start
     * @param errorCallback called in case of error
     */
    zoomTo(zoom: number, successCallback?: () => void, errorCallback?: (e: string) => void): void;
    /**
     * Get the current map bounds
     * @param resultCallback takes the map [[Bounds]] as argument
     * @param errorCallback called in case of error
     */
    getBounds(resultCallback?: (_bounds: Bounds) => void, errorCallback?: (_e: string) => void, id?: number): void;
    /**
     * Get the current map camera position
     * @param resultCallback takes the map [[CameraPosition]] as argument
     * @param errorCallback called in case of error
     */
    getCameraPosition(resultCallback: (cameraPosition: CameraPosition) => void, errorCallback?: (_e: string) => void): void;
    /**
     * Convert [[Coords]] to [[ScreenCoords]]
     * @param coords
     * @param resultCallback takes the [[ScreenCoords]] as argument
     * @param errorCallback called in case of error
     */
    convertCoordinates(coords: {
        lat: number;
        lng: number;
    }, resultCallback?: (_point: ScreenCoords) => void, errorCallback?: (_e: string) => void): void;
    /**
     * Convert [[ScreenCoords]] to [[Coords]]
     * @param point
     * @param successCallback takes the [[Coords]] as argument
     * @param errorCallback called in case of error
     */
    convertPoint(point: ScreenCoords, successCallback?: (_coords: {
        lat: number;
        lng: number;
    }) => void, errorCallback?: (_e: string) => void): void;
    /**
     * Adds a callback that's invoked when the map is flinged.
     * The user performs a bold move gesture and the maps move with more inertia.
     * @param listener
     * @param id
     */
    addOnFlingListener(listener: MapEventListener, id?: number): void;
    /**
     * Adds a callback that's invoked when the map is moved.
     * Does not fire when the map is flinged
     * @param listener
     * @param id
     */
    addOnMoveListener(listener: MapEventListener, id?: number): void;
    /**
     * Adds a callback that's invoked when the map is rotated.
     * @param listener
     * @param id
     */
    addOnRotateListener(listener: MapEventListener, id?: number): void;
    /**
     * Adds a callback that's invoked when the map is scaled.
     * @param listener
     * @param id
     */
    addOnScaleListener(listener: MapEventListener, id?: number): void;
    /**
     * This event is triggered when the map is about to start loading a new map style.
     * @param listener
     * @param id
     */
    addOnWillStartLoadingMapListener(listener: () => void, id?: number): void;
    /**
     * This event is triggered when the map will start rendering the map.
     * @param listener
     * @param id
     */
    addOnWillStartRenderingMapListener(listener: () => void, id?: number): void;
    /**
     * This event is triggered whenever the displayed map region is about to change without animation.
     * @param listener
     * @param id
     */
    addOnCameraWillChangeListener(listener: () => void, id?: number): void;
    /**
     * This event is triggered whenever the displayed map region finished changing without an animation.
     * @param listener
     * @param id
     */
    addOnCameraDidChangeListener(listener: () => void, id?: number): void;
    /**
     * Triggered when a style has finished loading.
     * @param listener
     * @param id
     */
    addOnDidFinishLoadingStyleListener(listener: () => void, id?: number): void;
    /**
     * Triggered when a source changes.
     * @param listener
     * @param id
     */
    addOnSourceChangedListener(listener: () => void, id?: number): void;
    /**
     * This event is triggered when the map will start rendering a frame.
     * @param listener
     * @param id
     */
    addOnWillStartRenderingFrameListener(listener: () => void, id?: number): void;
    /**
     * This event is triggered when the map finished rendering a frame.
     * @param listener
     * @param id
     */
    addOnDidFinishRenderingFrameListener(listener: () => void, id?: number): void;
    /**
     * This is triggered when the map has successfully loaded a new map style.
     * @param listener
     * @param id
     */
    addOnDidFinishLoadingMapListener(listener: () => void, id?: number): void;
    /**
     * This event is triggered when the map is fully rendered.
     * @param listener
     * @param id
     */
    addOnDidFinishRenderingMapListener(listener: (fullyRendered: boolean) => void, id?: number): void;
}
export declare const show: Vietmap['show'];
export declare const setDebug: Vietmap['setDebug'];
export declare const setClickable: Vietmap['setClickable'];
export declare const hide: Vietmap['hide'];
export declare const destroy: Vietmap['destroy'];
export declare const setContainer: Vietmap['setContainer'];
export declare const downloadRegion: Vietmap['downloadRegion'];
export declare const getOfflineRegionList: Vietmap['getOfflineRegionList'];
export declare const deleteOfflineRegion: Vietmap['deleteOfflineRegion'];
export declare const pauseDownload: Vietmap['pauseDownload'];
export declare const resumeDownload: Vietmap['resumeDownload'];
export declare const addMapClickCallback: Vietmap['addMapClickCallback'];
export declare const deselect: Vietmap['deselect'];
export declare const addImage: Vietmap['addImage'];
export declare const removeImage: Vietmap['removeImage'];
export declare const addLayer: Vietmap['addLayer'];
export declare const setLayoutProperty: Vietmap['setLayoutProperty'];
export declare const removeLayer: Vietmap['removeLayer'];
export declare const addSource: Vietmap['addSource'];
export declare const removeSource: Vietmap['removeSource'];
export declare const setGeoJson: Vietmap['setGeoJson'];
export declare const flyTo: Vietmap['flyTo'];
export declare const setCenter: Vietmap['setCenter'];
export declare const getCenter: Vietmap['getCenter'];
export declare const scrollMap: Vietmap['scrollMap'];
export declare const setPitch: Vietmap['setPitch'];
export declare const getPitch: Vietmap['getPitch'];
export declare const setZoom: Vietmap['setZoom'];
export declare const getZoom: Vietmap['getZoom'];
export declare const zoomTo: Vietmap['zoomTo'];
export declare const getBounds: Vietmap['getBounds'];
export declare const getCameraPosition: Vietmap['getCameraPosition'];
export declare const convertCoordinates: Vietmap['convertCoordinates'];
export declare const convertPoint: Vietmap['convertPoint'];
export declare const addOnFlingListener: Vietmap['addOnFlingListener'];
export declare const addOnMoveListener: Vietmap['addOnMoveListener'];
export declare const addOnRotateListener: Vietmap['addOnRotateListener'];
export declare const addOnScaleListener: Vietmap['addOnScaleListener'];
/**
 * This event is triggered when the map is about to start loading a new map style.
 */
export declare const addOnWillStartLoadingMapListener: Vietmap['addOnWillStartLoadingMapListener'];
/**
 * This event is triggered when the map will start rendering the map.
 */
export declare const addOnWillStartRenderingMapListener: Vietmap['addOnWillStartRenderingMapListener'];
/**
 * This event is triggered whenever the displayed map region is about to change without animation.
 */
export declare const addOnCameraWillChangeListener: Vietmap['addOnCameraWillChangeListener'];
/**
 * This event is triggered whenever the displayed map region finished changing without an animation.
 */
export declare const addOnCameraDidChangeListener: Vietmap['addOnCameraDidChangeListener'];
/**
 * Triggered when a style has finished loading.
 */
export declare const addOnDidFinishLoadingStyleListener: Vietmap['addOnDidFinishLoadingStyleListener'];
/**
 * Triggered when a source changes.
 */
export declare const addOnSourceChangedListener: Vietmap['addOnSourceChangedListener'];
/**
 * This event is triggered when the map will start rendering a frame.
 */
export declare const addOnWillStartRenderingFrameListener: Vietmap['addOnWillStartRenderingFrameListener'];
/**
 * This event is triggered when the map finished rendering a frame.
 */
export declare const addOnDidFinishRenderingFrameListener: Vietmap['addOnDidFinishRenderingFrameListener'];
/**
 * This is triggered when the map has successfully loaded a new map style.
 */
export declare const addOnDidFinishLoadingMapListener: Vietmap['addOnDidFinishLoadingMapListener'];
/**
 * This event is triggered when the map is fully rendered.
 */
export declare function addOnDidFinishRenderingMapListener(listener: (fully: boolean) => void, id?: number): void;
export {};
