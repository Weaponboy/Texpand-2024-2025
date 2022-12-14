package org.firstinspires.ftc.team6220_PowerPlay;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ConeDetectionPipeline extends OpenCvPipeline {
    public int centerX = 960;
    public double distance = 0;
    public double coneSize = 0;
    public boolean grab = false;
    private final int[] lowerBlue = {42, 128, 114};
    private final int[] upperBlue = {168, 242, 255};

    Scalar intToScalar(int[] a) {
        if (a.length != 3) {
            throw new IllegalArgumentException("Input must be a three-item array");
        }

        return new Scalar(a[0], a[1], a[2]);
    }

    public Mat maskFrame(Mat input, int[] lower, int[] upper) {
        Mat HSV = new Mat();
        Size blurSize = new Size(5, 5);
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_BGR2HSV);
        Imgproc.blur(HSV, HSV, blurSize);
        Core.inRange(HSV, intToScalar(lower), intToScalar(upper), HSV);
        return (HSV);
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat mask = maskFrame(input, lowerBlue, upperBlue);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        if (contours.size() > 0) {
            double maxVal = 0.0;
            int maxValIdx = 0;

            for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
                double contourArea = Imgproc.contourArea(contours.get(contourIdx));

                if (maxVal < contourArea) {
                    maxVal = contourArea;
                    maxValIdx = contourIdx;
                }
            }

            Rect boundingRect = Imgproc.boundingRect(contours.get(maxValIdx));
            Moments m = Imgproc.moments(contours.get(maxValIdx), false);

            if (m.get_m00() > 0) {
                double cX = (m.get_m10() / m.get_m00());
                distance = centerX - cX;
                coneSize = boundingRect.width * boundingRect.height;

                grab = Math.abs(distance) < 30 && coneSize < 3000;
            }
        }

        return input;
    }
}
