**[cordova-plugin-mapbox](../README.md)**

> [Globals](../README.md) / Mapbox

# Interface: Mapbox

## Hierarchy

* **Mapbox**

## Index

### Methods

* [addImage](mapbox.md#addimage)
* [addLayer](mapbox.md#addlayer)
* [addMapClickCallback](mapbox.md#addmapclickcallback)
* [addOnCameraDidChangeListener](mapbox.md#addoncameradidchangelistener)
* [addOnCameraWillChangeListener](mapbox.md#addoncamerawillchangelistener)
* [addOnDidFinishLoadingMapListener](mapbox.md#addondidfinishloadingmaplistener)
* [addOnDidFinishLoadingStyleListener](mapbox.md#addondidfinishloadingstylelistener)
* [addOnDidFinishRenderingFrameListener](mapbox.md#addondidfinishrenderingframelistener)
* [addOnDidFinishRenderingMapListener](mapbox.md#addondidfinishrenderingmaplistener)
* [addOnFlingListener](mapbox.md#addonflinglistener)
* [addOnMoveListener](mapbox.md#addonmovelistener)
* [addOnRotateListener](mapbox.md#addonrotatelistener)
* [addOnScaleListener](mapbox.md#addonscalelistener)
* [addOnSourceChangedListener](mapbox.md#addonsourcechangedlistener)
* [addOnWillStartLoadingMapListener](mapbox.md#addonwillstartloadingmaplistener)
* [addOnWillStartRenderingFrameListener](mapbox.md#addonwillstartrenderingframelistener)
* [addOnWillStartRenderingMapListener](mapbox.md#addonwillstartrenderingmaplistener)
* [addSource](mapbox.md#addsource)
* [convertCoordinates](mapbox.md#convertcoordinates)
* [convertPoint](mapbox.md#convertpoint)
* [deleteOfflineRegion](mapbox.md#deleteofflineregion)
* [deselect](mapbox.md#deselect)
* [destroy](mapbox.md#destroy)
* [downloadRegion](mapbox.md#downloadregion)
* [flyTo](mapbox.md#flyto)
* [getBounds](mapbox.md#getbounds)
* [getCameraPosition](mapbox.md#getcameraposition)
* [getCenter](mapbox.md#getcenter)
* [getOfflineRegionList](mapbox.md#getofflineregionlist)
* [getPitch](mapbox.md#getpitch)
* [getZoom](mapbox.md#getzoom)
* [hide](mapbox.md#hide)
* [pauseDownload](mapbox.md#pausedownload)
* [removeImage](mapbox.md#removeimage)
* [removeLayer](mapbox.md#removelayer)
* [removeSource](mapbox.md#removesource)
* [resumeDownload](mapbox.md#resumedownload)
* [scrollMap](mapbox.md#scrollmap)
* [setCenter](mapbox.md#setcenter)
* [setClickable](mapbox.md#setclickable)
* [setContainer](mapbox.md#setcontainer)
* [setDebug](mapbox.md#setdebug)
* [setGeoJson](mapbox.md#setgeojson)
* [setLayoutProperty](mapbox.md#setlayoutproperty)
* [setPitch](mapbox.md#setpitch)
* [setZoom](mapbox.md#setzoom)
* [show](mapbox.md#show)
* [zoomTo](mapbox.md#zoomto)

## Methods

### addImage

▸ **addImage**(`imageId`: string, `image`: [ImageProperties](../README.md#imageproperties), `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:331](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L331)*

Add an image to the map style. For instance, a marker image.
You must add the image before referencing it in a resource.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`imageId` | string |  |
`image` | [ImageProperties](../README.md#imageproperties) |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### addLayer

▸ **addLayer**(`layer`: [Layer](../README.md#layer), `beforeId?`: number, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:355](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L355)*

Add a layer to the map style

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`layer` | [Layer](../README.md#layer) |  |
`beforeId?` | number | the layer will be added below |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### addMapClickCallback

▸ **addMapClickCallback**(`callback?`: (featureCollection: FeatureCollection<GeoJSON.GeometryObject\>) => void, `errorCallback?`: (e: string) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:309](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L309)*

Add a map click callback to the native map.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`callback?` | (featureCollection: FeatureCollection<GeoJSON.GeometryObject\>) => void | the click callback taking a collection of JSON feature geometry objects. |
`errorCallback?` | (e: string) => void | called in case of error  |
`id?` | number | - |

**Returns:** void

___

### addOnCameraDidChangeListener

▸ **addOnCameraDidChangeListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:613](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L613)*

This event is triggered whenever the displayed map region finished changing without an animation.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnCameraWillChangeListener

▸ **addOnCameraWillChangeListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:607](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L607)*

This event is triggered whenever the displayed map region is about to change without animation.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnDidFinishLoadingMapListener

▸ **addOnDidFinishLoadingMapListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:643](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L643)*

This is triggered when the map has successfully loaded a new map style.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnDidFinishLoadingStyleListener

▸ **addOnDidFinishLoadingStyleListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:619](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L619)*

Triggered when a style has finished loading.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnDidFinishRenderingFrameListener

▸ **addOnDidFinishRenderingFrameListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:637](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L637)*

This event is triggered when the map finished rendering a frame.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnDidFinishRenderingMapListener

▸ **addOnDidFinishRenderingMapListener**(`listener`: (fullyRendered: boolean) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:649](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L649)*

This event is triggered when the map is fully rendered.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | (fullyRendered: boolean) => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnFlingListener

▸ **addOnFlingListener**(`listener`: [MapEventListener](../README.md#mapeventlistener), `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:570](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L570)*

Adds a callback that's invoked when the map is flinged.
The user performs a bold move gesture and the maps move with more inertia.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | [MapEventListener](../README.md#mapeventlistener) |  |
`id?` | number |   |

**Returns:** void

___

### addOnMoveListener

▸ **addOnMoveListener**(`listener`: [MapEventListener](../README.md#mapeventlistener), `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:577](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L577)*

Adds a callback that's invoked when the map is moved.
Does not fire when the map is flinged

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | [MapEventListener](../README.md#mapeventlistener) |  |
`id?` | number |   |

**Returns:** void

___

### addOnRotateListener

▸ **addOnRotateListener**(`listener`: [MapEventListener](../README.md#mapeventlistener), `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:583](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L583)*

Adds a callback that's invoked when the map is rotated.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | [MapEventListener](../README.md#mapeventlistener) |  |
`id?` | number |   |

**Returns:** void

___

### addOnScaleListener

▸ **addOnScaleListener**(`listener`: [MapEventListener](../README.md#mapeventlistener), `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:589](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L589)*

Adds a callback that's invoked when the map is scaled.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | [MapEventListener](../README.md#mapeventlistener) |  |
`id?` | number |   |

**Returns:** void

___

### addOnSourceChangedListener

▸ **addOnSourceChangedListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:625](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L625)*

Triggered when a source changes.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnWillStartLoadingMapListener

▸ **addOnWillStartLoadingMapListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:595](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L595)*

This event is triggered when the map is about to start loading a new map style.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnWillStartRenderingFrameListener

▸ **addOnWillStartRenderingFrameListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:631](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L631)*

This event is triggered when the map will start rendering a frame.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addOnWillStartRenderingMapListener

▸ **addOnWillStartRenderingMapListener**(`listener`: () => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:601](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L601)*

This event is triggered when the map will start rendering the map.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`listener` | () => void |  |
`id?` | number |   |

**Returns:** void

___

### addSource

▸ **addSource**(`sourceId`: string, `source`: [Source](../README.md#source)<any, any\>, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:393](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L393)*

Add a source to the map style

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`sourceId` | string |  |
`source` | [Source](../README.md#source)<any, any\> |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### convertCoordinates

▸ **convertCoordinates**(`coords`: { lat: number ; lng: number  }, `resultCallback?`: (\_point: [ScreenCoords](../README.md#screencoords)) => void, `errorCallback?`: (\_e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:545](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L545)*

Convert [Coords](../README.md#coords) to [ScreenCoords](../README.md#screencoords)

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`coords` | { lat: number ; lng: number  } |  |
`resultCallback?` | (\_point: [ScreenCoords](../README.md#screencoords)) => void | takes the [ScreenCoords](../README.md#screencoords) as argument |
`errorCallback?` | (\_e: string) => void | called in case of error  |

**Returns:** void

___

### convertPoint

▸ **convertPoint**(`point`: [ScreenCoords](../README.md#screencoords), `successCallback?`: (\_coords: { lat: number ; lng: number  }) => void, `errorCallback?`: (\_e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:559](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L559)*

Convert [ScreenCoords](../README.md#screencoords) to [Coords](../README.md#coords)

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`point` | [ScreenCoords](../README.md#screencoords) |  |
`successCallback?` | (\_coords: { lat: number ; lng: number  }) => void | takes the [Coords](../README.md#coords) as argument |
`errorCallback?` | (\_e: string) => void | called in case of error  |

**Returns:** void

___

### deleteOfflineRegion

▸ **deleteOfflineRegion**(`params?`: [OfflineRegionParams](../README.md#offlineregionparams), `successCallback?`: (isDeleted: boolean) => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:276](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L276)*

Delete an offline region

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`params?` | [OfflineRegionParams](../README.md#offlineregionparams) |  |
`successCallback?` | (isDeleted: boolean) => void | a callback taking the result boolean of the deletion |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### deselect

▸ **deselect**(`callback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:319](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L319)*

Deselect the current feature of the map.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`callback?` | () => void | - |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### destroy

▸ **destroy**(`successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:230](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L230)*

Destroy a map and free the memory.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### downloadRegion

▸ **downloadRegion**(`options`: [DownloadParams](../README.md#downloadparams), `statusCallback`: (state: [DownloadState](../README.md#downloadstate)) => void, `errorCallback?`: (e: string \| { reason: \"REGION\_EXISTS\"  }) => void): void

*Defined in [cordova-plugin-mapbox.ts:254](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L254)*

Download a region delimited by [Bounds](../README.md#bounds)()

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`options` | [DownloadParams](../README.md#downloadparams) |  |
`statusCallback` | (state: [DownloadState](../README.md#downloadstate)) => void | - |
`errorCallback?` | (e: string \| { reason: \"REGION\_EXISTS\"  }) => void | called in case of error  |

**Returns:** void

___

### flyTo

▸ **flyTo**(`cameraPosition`: Partial<[CameraPosition](../README.md#cameraposition)\> & { duration: number  }, `successCallback?`: () => void, `errorCallback?`: (e: string) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:429](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L429)*

Smoothly jump to a position on the map.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`cameraPosition` | Partial<[CameraPosition](../README.md#cameraposition)\> & { duration: number  } |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |
`id?` | number | - |

**Returns:** void

___

### getBounds

▸ **getBounds**(`resultCallback?`: (\_bounds: [Bounds](../README.md#bounds)) => void, `errorCallback?`: (\_e: string) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:525](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L525)*

Get the current map bounds

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`resultCallback?` | (\_bounds: [Bounds](../README.md#bounds)) => void | takes the map [Bounds](../README.md#bounds) as argument |
`errorCallback?` | (\_e: string) => void | called in case of error  |
`id?` | number | - |

**Returns:** void

___

### getCameraPosition

▸ **getCameraPosition**(`resultCallback`: (cameraPosition: [CameraPosition](../README.md#cameraposition)) => void, `errorCallback?`: (\_e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:535](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L535)*

Get the current map camera position

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`resultCallback` | (cameraPosition: [CameraPosition](../README.md#cameraposition)) => void | takes the map [CameraPosition](../README.md#cameraposition) as argument |
`errorCallback?` | (\_e: string) => void | called in case of error  |

**Returns:** void

___

### getCenter

▸ **getCenter**(`resultCallback`: (center: [Coords](../README.md#coords)) => void, `errorCallback?`: (\_e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:451](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L451)*

Get the [Coords](../README.md#coords) of the map center.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`resultCallback` | (center: [Coords](../README.md#coords)) => void | takes a [Coords](../README.md#coords) argument. |
`errorCallback?` | (\_e: string) => void | called in case of error  |

**Returns:** void

___

### getOfflineRegionList

▸ **getOfflineRegionList**(`styleUrl?`: string, `successCallback?`: (regionNames: string[]) => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:265](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L265)*

Get the availble names of offline region.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`styleUrl?` | string | the Mapbox style URL |
`successCallback?` | (regionNames: string[]) => void | a callback taking a string Array of region names. |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### getPitch

▸ **getPitch**(`resultCallback?`: (pitch: number) => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:482](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L482)*

Get the current map pitch

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`resultCallback?` | (pitch: number) => void | takes a the current pitch as argument. |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### getZoom

▸ **getZoom**(`resultCallback?`: (zoom: number) => void, `errorCallback?`: (e: string) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:504](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L504)*

Return the current map zoom

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`resultCallback?` | (zoom: number) => void | takes a the current zoom as argument. |
`errorCallback?` | (e: string) => void | called in case of error  |
`id?` | number | - |

**Returns:** void

___

### hide

▸ **hide**(`successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:221](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L221)*

Hide the map. The map is still in memory.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### pauseDownload

▸ **pauseDownload**(`options?`: [OfflineRegionParams](../README.md#offlineregionparams), `successCallback?`: () => void, `errorCallback?`: (e: string) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:287](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L287)*

Pause the download of a region

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`options?` | [OfflineRegionParams](../README.md#offlineregionparams) |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |
`id?` | number | - |

**Returns:** void

___

### removeImage

▸ **removeImage**(`imageId`: string, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:343](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L343)*

Remove an image from the map style.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`imageId` | string |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### removeLayer

▸ **removeLayer**(`layerId`: string, `successCallback?`: () => void, `errorCallback?`: (\_e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:381](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L381)*

Remove a layer from the map style

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`layerId` | string |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (\_e: string) => void | called in case of error  |

**Returns:** void

___

### removeSource

▸ **removeSource**(`sourceId`: string, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:405](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L405)*

Remove a source from the map style

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`sourceId` | string |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### resumeDownload

▸ **resumeDownload**(`options?`: [OfflineRegionParams](../README.md#offlineregionparams), `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:299](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L299)*

Resume the paused download of a region

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`options?` | [OfflineRegionParams](../README.md#offlineregionparams) |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### scrollMap

▸ **scrollMap**(`delta`: [number, number], `successCallback`: (center: [Coords](../README.md#coords)) => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:461](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L461)*

Animate a camera translation.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`delta` | [number, number] |  |
`successCallback` | (center: [Coords](../README.md#coords)) => void | called when animation start |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### setCenter

▸ **setCenter**(`center`: [LngLat](../README.md#lnglat), `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:441](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L441)*

Center the map on another [LngLat](../README.md#lnglat) coordinates. (not animated)

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`center` | [LngLat](../README.md#lnglat) | the new coordinautes |
`successCallback?` | () => void | called when animation start |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### setClickable

▸ **setClickable**(`isClickable`: boolean, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:211](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L211)*

Set a map as clickable. If false, the user won't be abble to move the map
with gesture.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`isClickable` | boolean |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### setContainer

▸ **setContainer**(`params`: [MapboxContainerParams](../README.md#mapboxcontainerparams), `successCallback?`: () => void, `errorCallback?`: (e: string) => void, `id?`: number): void

*Defined in [cordova-plugin-mapbox.ts:242](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L242)*

Set the HTML container of the Map. It will resize
the map to fit in the container and update the clickable
HTML elements overlaying the map.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`params` | [MapboxContainerParams](../README.md#mapboxcontainerparams) |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |
`id?` | number | - |

**Returns:** void

___

### setDebug

▸ **setDebug**(`isDebug`: boolean, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:199](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L199)*

Colorize clickable HTML elements with for debug purpose.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`isDebug` | boolean |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### setGeoJson

▸ **setGeoJson**(`sourceId`: string, `geoJson`: [SourceData](../README.md#sourcedata)<any, any\>, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:417](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L417)*

Set the data of a source. Use this when you want to animate data.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`sourceId` | string |  |
`geoJson` | [SourceData](../README.md#sourcedata)<any, any\> |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### setLayoutProperty

▸ **setLayoutProperty**(`layerId`: string, `property`: [LayoutProperty](../README.md#layoutproperty), `successCallback?`: () => void, `errorCallback?`: (\_e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:369](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L369)*

Set a layout property for a layer

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`layerId` | string |  |
`property` | [LayoutProperty](../README.md#layoutproperty) | - |
`successCallback?` | () => void | called on success |
`errorCallback?` | (\_e: string) => void | called in case of error  |

**Returns:** void

___

### setPitch

▸ **setPitch**(`pitch`: number, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:472](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L472)*

Set the map pitch (not animated)

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`pitch` | number |  |
`successCallback?` | () => void | called on success |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### setZoom

▸ **setZoom**(`zoom`: number, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:494](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L494)*

Set the zoom of the map (not animated)

#### Parameters:

Name | Type |
------ | ------ |
`zoom` | number |
`successCallback?` | () => void |
`errorCallback?` | (e: string) => void |

**Returns:** void

___

### show

▸ **show**(`options`: [MapOptions](../README.md#mapoptions), `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:188](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L188)*

Display the map. Create it if needed.

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`options` | [MapOptions](../README.md#mapoptions) |  |
`successCallback?` | () => void | called when the map is displayed |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void

___

### zoomTo

▸ **zoomTo**(`zoom`: number, `successCallback?`: () => void, `errorCallback?`: (e: string) => void): void

*Defined in [cordova-plugin-mapbox.ts:515](https://github.com/dagatsoin/cordova-plugin-mapbox/blob/801e8e0/src/js/cordova-plugin-mapbox.ts#L515)*

#### Parameters:

Name | Type | Description |
------ | ------ | ------ |
`zoom` | number |  |
`successCallback?` | () => void | called when animation start |
`errorCallback?` | (e: string) => void | called in case of error  |

**Returns:** void
