### Basic working version of the android app

The app follows the pattern of a single-activity app, that consists of two fragments: camera and result. It also has a viewmodel, which manages data between the two sections.
The camera fragment uses the Android library CameraX to allow the user to take photos that will be passed to the TensorFlow Lite model, which will generate a prediction. The result will be shown to the user in the result fragment.
The ML model resides within the viewmodel and the prediction is stored in LiveData object that is observed by the result fragment.
Transitions between the fragments are handled by the Android Navigation component.

