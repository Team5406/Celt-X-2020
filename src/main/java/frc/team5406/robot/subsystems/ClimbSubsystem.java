/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team5406.robot.subsystems;

import frc.team5406.robot.Constants;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;


public class ClimbSubsystem extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  private static CANSparkMax climbMotor = new CANSparkMax(Constants.CLIMBER_MOTOR, MotorType.kBrushless);
  private static CANEncoder climbEncoder;

  private static CANPIDController leftClimbPID, rightClimbPID;
  private static Solenoid climbCylinder;

  public static void setupMotors() {
    climbMotor.setIdleMode(IdleMode.kBrake);
    climbMotor.setSmartCurrentLimit(50);

    climbEncoder = climbMotor.getEncoder();

    leftClimbPID = climbMotor.getPIDController();

    leftClimbPID.setP(Constants.LEFT_CLIMBER_PID0_P, 0);
    leftClimbPID.setI(Constants.LEFT_CLIMBER_PID0_I, 0);
    leftClimbPID.setD(Constants.LEFT_CLIMBER_PID0_D, 0);
    leftClimbPID.setIZone(0, 0);
    leftClimbPID.setFF(Constants.LEFT_CLIMBER_PID0_F, 0);
    leftClimbPID.setOutputRange(Constants.OUTPUT_RANGE_MIN, Constants.OUTPUT_RANGE_MAX, 0);

    leftClimbPID.setSmartMotionMaxVelocity(5000, 0);
    leftClimbPID.setSmartMotionMaxAccel(10000, 0);
    leftClimbPID.setSmartMotionAllowedClosedLoopError(0.2, 0);

    climbEncoder.setPosition(0);

    climbCylinder = new Solenoid(Constants.CLIMB_CYLINDER);

    climbMotor.burnFlash();

  }

  public static void setIntakePosition(boolean out){
    climbCylinder.set(out);
  }

  public static void setBrake() {

    setIntakePosition(Constants.SET_BRAKE);
  }

  public static void releaseBrake() {
    System.out.println("release brake");

    setIntakePosition(Constants.RELEASE_BRAKE);
  }


  // Set Speed For Both
  public static void setSpeed(double left) {
    System.out.println("Set climb speed " +left);
    climbMotor.set(left);
  }

  // Set Left & Right Speed
  public static void setPosition(double position) {
    leftClimbPID.setReference(position, ControlType.kSmartMotion, 0);
  }

  //returns the position
  public static double getClimberPosition(){
    return climbEncoder.getPosition();
  }

  public static double getClimberVelocity() {

    return climbEncoder.getVelocity();
  }


  public static void resetPosition() {
    System.out.println("Reset Climb Position");
     climbEncoder.setPosition(0);
  }


    public ClimbSubsystem() {
setupMotors();
  }

  @Override
  public void periodic() {
  }
}
