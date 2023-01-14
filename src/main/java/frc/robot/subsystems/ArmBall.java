package frc.robot.subsystems;
import frc.robot.RobotMap;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Ball Carriage Lifting Arm, Limit Switch and Motor Controller
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class ArmBall extends SubsystemBase {
    //motor for Arm
    private final WPI_TalonFX m_carriageArm = new WPI_TalonFX(RobotMap.TALON_CARRIAGE_ARM);
    //limitSwitch for Arm movements
    public DigitalInput m_topLimitSwitch = new DigitalInput(0);
    public DigitalInput m_bottomLimitSwitch = new DigitalInput(1);

    double curPosn = m_carriageArm.getSelectedSensorPosition(0);

    public ArmBall()
    {
    }

    public void armUp() {
        System.out.println("ArmUp Called");
        if(m_topLimitSwitch.get()){
            m_carriageArm.set(-0.4);
        }
        else {
            m_carriageArm.set(-0.1);
        }
    }

    public void armDown() {
        if(m_bottomLimitSwitch.get()){
            m_carriageArm.set(-0.05);
        }
        else {
            m_carriageArm.set(0);
        }
    }

    public void armStop() {
        m_carriageArm.set(0);
    }
}

