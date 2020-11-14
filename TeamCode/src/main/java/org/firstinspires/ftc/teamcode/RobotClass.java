package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtGyroSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;


public class RobotClass {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private double ticks = 537;//537
    BNO055IMU imu;

    public Telemetry telemetry;
    ColorSensor colorSensor;

    public RobotClass(HardwareMap hardwareMap, Telemetry telemetry) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft" );
        frontRight = hardwareMap.get(DcMotor.class, "frontRight" );
        backLeft = hardwareMap.get(DcMotor.class, "backLeft" );
        backRight = hardwareMap.get(DcMotor.class, "backRight" );

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        this.telemetry = telemetry;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

    }
    public double getAngleFromGyro() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    public void forward (double speed, double rotations){
        int leftCurrent = frontLeft.getCurrentPosition();
        int rightCurrent = frontRight.getCurrentPosition();
        int backLeftCurrent = backLeft.getCurrentPosition();
        int backRightCurrent = backRight.getCurrentPosition();

        double toPositionLeft = leftCurrent + rotations*ticks;
        double toPositionRight = rightCurrent + rotations*ticks;
        double toPositionbackLeft = backLeftCurrent + rotations*ticks;
        double toPositionbackRight = backRightCurrent + rotations*ticks;

        frontLeft.setTargetPosition((int)toPositionLeft);
        frontRight.setTargetPosition((int)toPositionRight);
        backLeft.setTargetPosition((int)toPositionbackLeft);
        backRight.setTargetPosition((int)toPositionbackRight);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(Math.abs(speed));
        frontRight.setPower(Math.abs(speed));
        backLeft.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(speed));

        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
        telemetry.addData("Target Front Right Motor Position", toPositionRight);
        telemetry.addData("Target Back Left Motor Position", toPositionbackLeft);
        telemetry.addData("Target Front Left Motor Position", toPositionbackLeft);
        telemetry.update();
    }
    public void backwards (double speed, double rotations) {
        int leftCurrent = frontLeft.getCurrentPosition();
        int rightCurrent = frontRight.getCurrentPosition();
        int backLeftCurrent = backLeft.getCurrentPosition();
        int backRightCurrent = backRight.getCurrentPosition();

        double toPositionLeft = leftCurrent - rotations * ticks;
        double toPositionRight = rightCurrent - rotations * ticks;
        double toPositionbackLeft = backLeftCurrent - rotations * ticks;
        double toPositionbackRight = backRightCurrent - rotations * ticks;

        frontLeft.setTargetPosition((int) toPositionLeft);
        frontRight.setTargetPosition((int) toPositionRight);
        backLeft.setTargetPosition((int) toPositionbackLeft);
        backRight.setTargetPosition((int) toPositionbackRight);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

//        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
//        telemetry.addData("Target Front Right Motor Position", toPositionRight);
//        telemetry.addData("Target Back Left Motor Position", toPositionBackLeft);
//        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
//        telemetry.update();
    }
        public void setSpeedForTurnRight (double speed) {
            frontLeft.setPower(speed);
            frontRight.setPower(-speed);
            backLeft.setPower(speed);
            backRight.setPower(-speed);
        }

        public void setSpeedForTurnLeft (double speed) {
            frontLeft.setPower(-speed);
            frontRight.setPower(speed);
            backLeft.setPower(-speed);
            backRight.setPower(speed);
        }

        public void stopMotors () {
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }

        public void turnRight (double speed, double angle) {
        double anglemult = 1.5;

            int leftCurrent = frontLeft.getCurrentPosition();
            int rightCurrent = frontRight.getCurrentPosition();
            int backLeftCurrent = backLeft.getCurrentPosition();
            int backRightCurrent = backRight.getCurrentPosition();

            double toPositionLeft = leftCurrent + anglemult  * angle;
            double toPositionRight = rightCurrent - anglemult * angle;
            double toPositionbackLeft = backLeftCurrent + anglemult * angle;
            double toPositionbackRight = backRightCurrent - anglemult * angle;

            frontLeft.setTargetPosition((int) toPositionLeft);
            frontRight.setTargetPosition((int) toPositionRight);
            backLeft.setTargetPosition((int) toPositionbackLeft);
            backRight.setTargetPosition((int) toPositionbackRight);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);

//        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
//        telemetry.addData("Target Front Right Motor Position", toPositionRight);
//        telemetry.addData("Target Back Left Motor Position", toPositionBackLeft);
//        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
//        telemetry.update();
    }
    public void turnLeft (double speed, double angle) {
        double anglemult = 1.5;

        int leftCurrent = frontLeft.getCurrentPosition();
        int rightCurrent = frontRight.getCurrentPosition();
        int backLeftCurrent = backLeft.getCurrentPosition();
        int backRightCurrent = backRight.getCurrentPosition();

        double toPositionLeft = leftCurrent - anglemult * angle;
        double toPositionRight = rightCurrent + anglemult * angle;
        double toPositionBackLeft = backLeftCurrent - anglemult * angle;
        double toPositionBackRight = backRightCurrent + anglemult * angle;

        frontLeft.setTargetPosition((int) toPositionLeft);
        frontRight.setTargetPosition((int) toPositionRight);
        backLeft.setTargetPosition((int) toPositionBackLeft);
        backRight.setTargetPosition((int) toPositionBackRight);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

//        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
//        telemetry.addData("Target Front Right Motor Position", toPositionRight);
//        telemetry.addData("Target Back Left Motor Position", toPositionBackLeft);
//        telemetry.addData("Target Front Left Motor Position", toPositionLeft);
//        telemetry.update();
    }

    public void pivotRightSloppy (double speed, double angle) throws InterruptedException {
        frontLeft.setPower(speed);
        frontRight.setPower(-speed);
        backLeft.setPower(speed);
        backRight.setPower(-speed);

        while (getAngleFromGyro() < angle) {
            wait(5);
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        frontLeft.setPower(0);

        telemetry.addData("Gyro Angle", getAngleFromGyro());
        telemetry.update();
    }

    public void pivotLeftSloppy (double speed, double angle) throws InterruptedException {
        setSpeedForTurnLeft(speed);

        while (getAngleFromGyro() < angle) {
            wait(5);
        }

        stopMotors();

        telemetry.addData("Gyro Angle", getAngleFromGyro());
        telemetry.update();
        }
//
//    public void pivotLeft (double speed, double angle) throws InterruptedException {
//        telemetry.addData("Prior Gyro Angle: ", getAngleFromGyro());
//
//        frontLeft.setPower(-speed);
//        frontRight.setPower(speed);
//        backLeft.setPower(-speed);
//        backRight.setPower(speed);
//
//        while (getAngleFromGyro() < angle) {
//            wait(5);
//        }
//
//        frontLeft.setPower(0);
//        frontRight.setPower(0);
//        backLeft.setPower(0);
//        frontLeft.setPower(0);
//
//        telemetry.addData("Middle Gyro Angle: ", getAngleFromGyro());
//        telemetry.update();
//
//        frontLeft.setPower(-speed*.5);
//        frontRight.setPower(speed*.5);
//        backLeft.setPower(-speed*.5);
//        backRight.setPower(speed*.5);
//        while (getAngleFromGyro() < angle) {
//            wait(5);
//            telemetry.addData("Correcting Gyro Angle: ", getAngleFromGyro());
//            telemetry.update();
//        }
//
//        frontLeft.setPower(0);
//        frontRight.setPower(0);
//        backLeft.setPower(0);
//        frontLeft.setPower(0);
//        telemetry.addData("Completed Gyro Angle: ", getAngleFromGyro());
//        telemetry.update();
//    }
    public void forwardToWhite (double speed, double rotations, double speed2) throws InterruptedException {
        forward(speed,rotations);
        frontLeft.setPower(speed2);
        frontRight.setPower(speed2);
        backLeft.setPower(speed2);
        backRight.setPower(speed2);

        while (colorSensor.alpha() < 20) {
            wait(5);
            telemetry.addData("Light Level: ", colorSensor.alpha());
            telemetry.update();
        }

        stopMotors();
    }
    public void forwardToBlue (double speed, double rotations, double speed2) throws InterruptedException {
        forward(speed,rotations);
        frontLeft.setPower(speed2);
        frontRight.setPower(speed2);
        backLeft.setPower(speed2);
        backRight.setPower(speed2);

        while (colorSensor.blue() < 20) {
            wait(5);
            telemetry.addData("Blue Level: ", colorSensor.blue());
            telemetry.update();
        }

        stopMotors();
    }
    public void forwardToRed (double speed, double rotations, double speed2) throws InterruptedException {
        forward(speed,rotations);
        frontLeft.setPower(speed2);
        frontRight.setPower(speed2);
        backLeft.setPower(speed2);
        backRight.setPower(speed2);

        while (colorSensor.red() < 20) {
            wait(5);
            telemetry.addData("Red Level: ", colorSensor.red());
            telemetry.update();
        }

        stopMotors();
    }
    public void mecanumWitchcraft (double degree, double time) throws InterruptedException {
        double x = java.lang.Math.cos(degree);
        double y = java.lang.Math.sin(degree);

        frontLeft.setPower(y + x);
        backLeft.setPower(y - x);
        frontRight.setPower(y - x);
        backRight.setPower(y + x);

        wait((long) time);

        stopMotors();

    }
    public void mecanumWitchcraftColor (double degree, double rotations, int color) throws InterruptedException {
        double x = java.lang.Math.cos(degree);
        double y = java.lang.Math.sin(degree);

        frontLeft.setPower(y + x);
        backLeft.setPower(y - x);
        frontRight.setPower(y - x);
        backRight.setPower(y + x);

        if (color == 1) {
            while (colorSensor.alpha() < 20) {
                wait(5);
            }
        } else if (color == 2)  {
            while (colorSensor.red() < 20) {
                wait(5);
            }
        } else {
            while (colorSensor.blue() < 20) {
                wait(5);
            }
        }
        stopMotors();
    }
    public void strafeLeft (double speed, double rotations) {

        int leftCurrent = frontLeft.getCurrentPosition();
        int rightCurrent = frontRight.getCurrentPosition();
        int backLeftCurrent = backLeft.getCurrentPosition();
        int backRightCurrent = backRight.getCurrentPosition();

        double toPositionLeft = leftCurrent + rotations*ticks;
        double toPositionRight = rightCurrent + rotations*ticks;
        double toPositionbackLeft = backLeftCurrent + rotations*ticks;
        double toPositionbackRight = backRightCurrent + rotations*ticks;

        frontLeft.setTargetPosition((int)toPositionLeft);
        frontRight.setTargetPosition((int)toPositionRight);
        backLeft.setTargetPosition((int)toPositionbackLeft);
        backRight.setTargetPosition((int)toPositionbackRight);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(Math.abs(-speed));
        frontRight.setPower(Math.abs(speed));
        backLeft.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(-speed));

    }
    public void strafeRight (double speed, double rotations) {

        int leftCurrent = frontLeft.getCurrentPosition();
        int rightCurrent = frontRight.getCurrentPosition();
        int backLeftCurrent = backLeft.getCurrentPosition();
        int backRightCurrent = backRight.getCurrentPosition();

        double toPositionLeft = leftCurrent + rotations*ticks;
        double toPositionRight = rightCurrent + rotations*ticks;
        double toPositionbackLeft = backLeftCurrent + rotations*ticks;
        double toPositionbackRight = backRightCurrent + rotations*ticks;

        frontLeft.setTargetPosition((int)toPositionLeft);
        frontRight.setTargetPosition((int)toPositionRight);
        backLeft.setTargetPosition((int)toPositionbackLeft);
        backRight.setTargetPosition((int)toPositionbackRight);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(Math.abs(speed));
        frontRight.setPower(Math.abs(-speed));
        backLeft.setPower(Math.abs(-speed));
        backRight.setPower(Math.abs(speed));

    }
    }




