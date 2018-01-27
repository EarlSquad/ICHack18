package org.earlsquad.ichack18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.opencv.android.*;
import org.opencv.core.Mat;

public class CameraActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
  private static final String TAG = "CameraActivity";

  private CameraBridgeViewBase cameraView;
  private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) {
      switch (status) {
        case LoaderCallbackInterface.SUCCESS: {
          Log.i(TAG, "OpenCV loaded successfully");
          cameraView.enableView();
        }
        break;
        default: {
          super.onManagerConnected(status);
        }
        break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    cameraView = new JavaCameraView(this, -1);
    setContentView(cameraView);
    cameraView.setCvCameraViewListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    if (!OpenCVLoader.initDebug()) {
      Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, loaderCallback);
    } else {
      Log.d(TAG, "OpenCV library found inside package. Using it!");
      loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (cameraView != null)
      cameraView.disableView();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (cameraView != null)
      cameraView.disableView();
  }

  @Override
  public void onCameraViewStarted(int width, int height) {

  }

  @Override
  public void onCameraViewStopped() {

  }

  @Override
  public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame cvCameraViewFrame) {
    return cvCameraViewFrame.rgba();
  }
}
