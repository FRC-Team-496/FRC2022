/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;

//import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveWithJoystick;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  AHRS ahrs;
  WPI_TalonFX front_left, front_right, rear_left, rear_right;
  MecanumDrive  m_drive;
  Encoder left_Encoder, right_Encoder;

  public DriveTrain() {
    super();
    front_left = new WPI_TalonFX(1);
    front_right = new WPI_TalonFX(2);
    rear_left = new WPI_TalonFX(3);
    rear_right = new WPI_TalonFX(4);
    

    m_drive = new MecanumDrive(front_left, rear_left, front_right, rear_right);
 
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveWithJoystick());
  }

  public void drive( double getX, double getY, double getZ) {
    m_drive.driveCartesian(getX, getY, getZ);
  }


  public void stop() {
    m_drive.driveCartesian(0, 0, 0);
  }


  public void log() {
    SmartDashboard.putData(ahrs);
    

  }


  }

