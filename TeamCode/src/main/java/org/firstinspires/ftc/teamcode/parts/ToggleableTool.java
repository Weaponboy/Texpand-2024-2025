package org.firstinspires.ftc.teamcode.parts;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.concurrent.atomic.AtomicReference;

import androidx.annotation.NonNull;

/**
 * Interface for simple button push toggleable tool
 */
public abstract class ToggleableTool<T extends DcMotorSimple> {
    private final T motor;
    private final double power;
    private final ToggleButtonReader reader;
    public void update() {
        reader.readValue();
        if (reader.getState()) {
            motor.setPower(power);
        } else {
            motor.setPower(0);
        }
    }

    /**
     * @param map pass this through, this will be handled by user opmode. hardwaremap instance.
     * @param toolGamepad same as above, instance of GamepadEx from FtcLib
     * @param tClass Either DcMotor or CRServo, any extension of DcMotorSimple
     * @param name Hardware map name of tool motor/CRServo
     * @param button button to be pushed for toggle, uses GamepadKeys.Button
     * @param power power motor should be set to upon toggle
     */
    public ToggleableTool(@NonNull HardwareMap map, GamepadEx toolGamepad, Class<T> tClass, String name, GamepadKeys.Button button, double power) {
        this.motor = map.get(tClass, name);
        this.reader = new ToggleButtonReader(toolGamepad, button);
        this.power = power;
    }
}
