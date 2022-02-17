/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

/* import edu.wpi.first.wpilibj.GenericHID.Hand; */
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveWithJoystick extends Command {
  public DriveWithJoystick() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.m_driveTrain.driveCurve(Robot.m_oi.getDriver().getRightY(), Robot.m_oi.getDriver().getLeftX(), Robot.m_oi.getDriver().getRightBumper());
    Robot.m_driveTrain.drive(0.8 * -Robot.m_oi.getDriver().getLeftX(), 0.8 * -Robot.m_oi.getDriver().getLeftY(), 0.8 *Robot.m_oi.getDriver().getRightX());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_driveTrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}
