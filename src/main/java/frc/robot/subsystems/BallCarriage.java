package frc.robot.subsystems;
import frc.robot.RobotMap;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class BallCarriage extends SubsystemBase {
  
    //operator motor for ball dump
    CANSparkMax ballMotor = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);  
    //motor for Arm

    public BallCarriage()
    {
    }

    public void runForward() {
        ballMotor.set(0.7);
        System.out.println("Forward");
    }

    public void runBackwards() {
        ballMotor.set(-0.7);
        System.out.println("Back");
    }

    public void stop() {
        ballMotor.stopMotor();
        System.out.println("STOP");
    }
}

