package org.earlsquad.ichack18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.opencv.android.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    Mat original = cvCameraViewFrame.rgba();
    Mat result = original.clone();
    Imgproc.adaptiveThreshold(cvCameraViewFrame.gray(), result, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 10);
    List<MatOfPoint> contours = new ArrayList<>(100);
    Imgproc.findContours(result, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    Collections.sort(contours, new Comparator<MatOfPoint>() {
      @Override
      public int compare(MatOfPoint mop1, MatOfPoint mop2) {
        int area1 = (int) (mop1.size().height * mop1.size().width);
        int area2 = (int) (mop2.size().height * mop2.size().width);
        if (area1 > area2) {
          return -1;
        }
        if (area1 < area2) {
          return 1;
        }
        return 0;
      }
    });
    MatOfPoint mop = contours.get(0);
    for (Point point : mop.toArray()) {
      Imgproc.circle(result, point, 5, new Scalar(255,0,0,1), 2);
    }
    return result;
  }
}
