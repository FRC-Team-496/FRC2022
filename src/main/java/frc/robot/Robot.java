/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//library for camera function
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.AnalogInput;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.BallCarriage;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkMaxLowLevel;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  public static OI m_oi = new OI();
  public static DriveTrain m_driveTrain = new DriveTrain();
  public static BallCarriage m_ballCarriage = new BallCarriage();

  private Command m_simpleAutonomousCommand;
  private Command m_simpleAutonomousSEQCommand;
  private Command m_driveCommand;
  private Command m_dropTime;
  private Command m_dumpCommand;
  private Command m_grabCommand;
  private final AnalogInput m_ultrasonic = new AnalogInput(0);
  private final AHRS m_ahrs = new AHRS();
  private Command m_selectedCommand;
  private Command m_dumpThenDrive;
  private Command m_stopBallMotorCommand;

  private SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_dumpCommand = new RunCommand(
      () ->
        m_ballCarriage.runForward(),
      m_ballCarriage);

    m_grabCommand = new RunCommand(
      () ->
        m_ballCarriage.runBackwards(),
      m_ballCarriage);

    m_stopBallMotorCommand = new InstantCommand(
      () ->
        m_ballCarriage.stop(),
      m_ballCarriage);

    // Submit a command to back up for five seconds.
    m_simpleAutonomousCommand = getReverseCommand(5);
    m_simpleAutonomousSEQCommand = getReverseCommand(5);
    //m_simpleAutonomousCommand.schedule();
    // Dump Ball Motor for one second.
    m_dropTime = getDropTime(1);
    

    m_dumpThenDrive = new SequentialCommandGroup (m_dropTime, m_stopBallMotorCommand, m_simpleAutonomousSEQCommand);

    CameraServer.startAutomaticCapture();
    m_chooser.setDefaultOption("Simple Reverse", m_simpleAutonomousCommand);
    m_chooser.addOption("Dump and Reverse", m_dumpThenDrive);
  
    m_oi.opA.whileActiveOnce(m_dumpCommand);
    m_oi.opB.whileActiveOnce(m_grabCommand);

    m_ballCarriage.setDefaultCommand (
      new RunCommand( () -> m_ballCarriage.stop(),
                      m_ballCarriage) );
    
  }

  @Override
  public void robotPeriodic()
  {
    //double dist = getDistance();
    //SmartDashboard.putNumber("distance", dist);
    SmartDashboard.putData(m_ahrs);
    SmartDashboard.putData(m_chooser);
    CommandScheduler.getInstance().run();
  }
  

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // Stop if we're not doing anything else.
    
    m_driveTrain.setDefaultCommand (
      new RunCommand( () -> m_driveTrain.stop(),
                      m_driveTrain) );

    m_selectedCommand = m_chooser.getSelected();
    if(m_selectedCommand != null){
      m_selectedCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();

  }

  @Override
  public void autonomousExit() {
    if (m_simpleAutonomousCommand != null) {
      m_simpleAutonomousCommand.cancel();
    }
  }

  @Override
  public void teleopInit() {
    // Set up to execute commands from the driver.
    m_driveCommand = new RunCommand(
        () ->
          m_driveTrain.drive (0.8 * - m_oi.getDriver().getLeftX(),
                              0.8 * - m_oi.getDriver().getLeftY(),
                              0.8 *   m_oi.getDriver().getRightX()),
        m_driveTrain);
    m_driveCommand.schedule();
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopExit() {
    // All stop.
    m_driveCommand.cancel();
    m_driveTrain.setDefaultCommand (
      new RunCommand( () -> m_driveTrain.stop(),
                      m_driveTrain) );
  }

  // Return a command to back up for time seconds.
  private Command getReverseCommand (double time)
  {
    RunCommand rev = new RunCommand (
        () ->  m_driveTrain.drive (0.8, 0, 0),
        m_driveTrain);

    WaitCommand wait = new WaitCommand (time);
    return new ParallelDeadlineGroup (wait, rev);
  }

  private Command getDropTime (double time)
  {
    WaitCommand wait = new WaitCommand (time);
    return new ParallelDeadlineGroup (wait, m_dumpCommand);
  }

  public double getDistance() {
    double rawValue = m_ultrasonic.getValue();
    double voltage_scale_factor = 1;
    double currentDistanceCentimeters = rawValue * voltage_scale_factor * 0.125;
    // double currentDistanceInches = rawValue * voltage_scale_factor * 0.0492;

    return currentDistanceCentimeters;
  }
  
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}