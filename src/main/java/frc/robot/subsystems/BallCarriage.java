package frc.robot.subsystems;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class BallCarriage extends SubsystemBase {
  
    //operator motor for ball dump
    CANSparkMax ballMotor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);  

    public BallCarriage()
    {
    }

    public void runForward() {
        ballMotor.setVoltage(12);
        System.out.println("Forward");
    }

    public void runBackwards() {
        ballMotor.setVoltage(-12);
        System.out.println("Back");
    }

    public void stop() {
        ballMotor.stopMotor();
        System.out.println("STOP");
    }
}

