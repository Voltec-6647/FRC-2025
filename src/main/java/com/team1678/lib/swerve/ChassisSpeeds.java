// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team1678.lib.swerve;

import com.team1678.lib.Util;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.geometry.Twist2d;

/**
 * Represents the speed of a robot chassis. Although this struct contains
 * similar members compared
 * to a Twist2d, they do NOT represent the same thing. Whereas a Twist2d
 * represents a change in pose
 * w.r.t to the robot frame of reference, this ChassisSpeeds struct represents a
 * velocity w.r.t to
 * the robot frame of reference.
 *
 * <p>
 * A strictly non-holonomic drivetrain, such as a differential drive, should
 * never have a vyMetersPerSecond
 * component because it can never move sideways. Holonomic drivetrains such as
 * swerve and mecanum
 * will often have all three components.
 */
@SuppressWarnings("MemberName")
public class ChassisSpeeds {
	/**
	 * Represents forward velocity w.r.t the robot frame of reference. (Fwd is +)
	 */
	public double vxMetersPerSecond;

	/**
	 * Represents sideways velocity w.r.t the robot frame of reference. (Left is +)
	 */
	public double vyMetersPerSecond;

	/** Represents the angular velocity of the robot frame. (CCW is +) */
	public double omegaRadiansPerSecond;

	/**
	 * Constructs a ChassisSpeeds with zeros for vxMetersPerSecond,
	 * vyMetersPerSecond, and theta.
	 */
	public ChassisSpeeds() {}

	/**
	 * Constructs a ChassisSpeeds object.
	 *
	 * @param vxMetersPerSecond     Forward velocity.
	 * @param vyMetersPerSecond     Sideways velocity.
	 * @param omegaRadiansPerSecond Angular velocity.
	 */
	public ChassisSpeeds(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond) {
		this.vxMetersPerSecond = vxMetersPerSecond;
		this.vyMetersPerSecond = vyMetersPerSecond;
		this.omegaRadiansPerSecond = omegaRadiansPerSecond;
	}

	public static edu.wpi.first.math.kinematics.ChassisSpeeds toLegacy(ChassisSpeeds chassisSpeeds){
		return new edu.wpi.first.math.kinematics.ChassisSpeeds(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond, chassisSpeeds.omegaRadiansPerSecond);
	}

	public static ChassisSpeeds fromLegacy(edu.wpi.first.math.kinematics.ChassisSpeeds chassisSpeeds){
		return new ChassisSpeeds(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond, chassisSpeeds.omegaRadiansPerSecond);
	}

	/**
	 * Converts a user provided field-relative set of speeds into a robot-relative
	 * ChassisSpeeds
	 * object.
	 *
	 * @param vxMetersPerSecond     The component of speed in the x direction
	 *                              relative to the field.
	 *                              Positive x is away from your alliance wall.
	 * @param vyMetersPerSecond     The component of speed in the y direction
	 *                              relative to the field.
	 *                              Positive y is to your left when standing behind
	 *                              the alliance wall.
	 * @param omegaRadiansPerSecond The angular rate of the robot.
	 * @param robotAngle            The angle of the robot as measured by a
	 *                              gyroscope. The robot's angle is
	 *                              considered to be zero when it is facing directly
	 *                              away from your alliance station wall.
	 *                              Remember that this should be CCW positive.
	 * @return ChassisSpeeds object representing the speeds in the robot's frame of
	 *         reference.
	 */
	public static ChassisSpeeds fromFieldRelativeSpeeds(
			double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond, Rotation2d robotAngle) {
		return new ChassisSpeeds(
				vxMetersPerSecond * robotAngle.cos() + vyMetersPerSecond * robotAngle.sin(),
				-vxMetersPerSecond * robotAngle.sin() + vyMetersPerSecond * robotAngle.cos(),
				omegaRadiansPerSecond);
	}

	public static ChassisSpeeds fromRobotRelativeSpeeds(
			double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond) {
		return new ChassisSpeeds(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond);
	}

	public Twist2d toTwist2d() {
		return new Twist2d(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond);
	}

	public boolean epsilonEquals(ChassisSpeeds other, double epsilon) {
		return Util.epsilonEquals(vxMetersPerSecond, other.vxMetersPerSecond, epsilon)
				&& Util.epsilonEquals(vyMetersPerSecond, other.vyMetersPerSecond, epsilon)
				&& Util.epsilonEquals(omegaRadiansPerSecond, other.omegaRadiansPerSecond, epsilon);
	}

	@Override
	public String toString() {
		return String.format(
				"ChassisSpeeds(Vx: %.2f m/s, Vy: %.2f m/s, Omega: %.2f rad/s)",
				vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond);
	}
}
