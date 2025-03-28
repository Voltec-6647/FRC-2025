package com.team6647.frc2025.auto.modes;

import com.team1678.frc2024.auto.AutoModeBase;
import com.team1678.frc2024.auto.AutoModeEndedException;
import com.team1678.frc2024.auto.actions.SwerveTrajectoryAction;
import com.team1678.frc2024.paths.TrajectoryGenerator1678;
import com.team254.lib.geometry.Pose2dWithMotion;
import com.team254.lib.trajectory.Trajectory254;
import com.team254.lib.trajectory.timing.TimedState;

public class TestPathMode extends AutoModeBase {

	Trajectory254<TimedState<Pose2dWithMotion>> trajectory1 =
			logTrajectory(TrajectoryGenerator1678.getInstance().getTrajectorySet().testTrajectory);
	Trajectory254<TimedState<Pose2dWithMotion>> trajectory2 =
			logTrajectory(TrajectoryGenerator1678.getInstance().getTrajectorySet().testTrajectory2);

	public TestPathMode() {}

	// spotless:off
	@Override
	protected void routine() throws AutoModeEndedException {
		System.out.println("Running test mode auto!");
		runAction(new SwerveTrajectoryAction(trajectory1, true));
		runAction(new SwerveTrajectoryAction(trajectory2, false));
		System.out.println("Finished auto!");
	}
	// spotless:on
}
