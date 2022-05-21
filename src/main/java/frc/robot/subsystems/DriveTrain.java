package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.RobotMap;



public class DriveTrain extends SubsystemBase {
  //AHRS ahrs;

  private final WPI_TalonFX m_frontLeft = new WPI_TalonFX(RobotMap.TALON_FRONT_LEFT);
  private final WPI_TalonFX m_frontRight = new WPI_TalonFX(RobotMap.TALON_FRONT_RIGHT);
  private final WPI_TalonFX m_rearLeft = new WPI_TalonFX(RobotMap.TALON_REAR_LEFT);
  private final WPI_TalonFX m_rearRight = new WPI_TalonFX(RobotMap.TALON_REAR_RIGHT);
  private final MecanumDrive m_drive = new MecanumDrive (m_frontLeft, m_frontRight, m_rearLeft, m_rearRight);

  public DriveTrain()
  {
  }

  
  public void drive(double vx, double vy, double omega) {
    m_drive.driveCartesian(vx, vy, omega);
  }


  public void stop() {
    m_drive.driveCartesian(0, 0, 0);
  }
}

