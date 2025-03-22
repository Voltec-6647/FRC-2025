package com.team6647.frc2025.auto.modes.configuredQuals;

import com.team1678.frc2024.auto.AutoModeBase;
import com.team1678.frc2024.auto.AutoModeEndedException;
import com.team1678.frc2024.auto.actions.LambdaAction;
import com.team1678.frc2024.auto.actions.ParallelAction;
import com.team1678.frc2024.auto.actions.RequestAction;
import com.team1678.frc2024.auto.actions.SeriesAction;
import com.team1678.frc2024.auto.actions.SwervePIDAction;
import com.team1678.frc2024.auto.actions.SwerveTrajectoryAction;
import com.team1678.frc2024.auto.actions.TurnInPlaceAction;
import com.team1678.frc2024.auto.actions.WaitAction;
import com.team1678.frc2024.auto.actions.WaitForSuperstructureAction;
import com.team1678.frc2024.auto.actions.WaitToPassXCoordinateAction;
import com.team1678.frc2024.auto.actions.WaitToPassYCoordinateAction;
import com.team1678.frc2024.subsystems.CoralPivot;
import com.team1678.frc2024.subsystems.Drive;
import com.team1678.frc2024.subsystems.Drive.DriveControlState;
import com.team1678.lib.requests.LambdaRequest;
import com.team1678.lib.requests.SequentialRequest;
import com.team1678.lib.requests.WaitRequest;
import com.team254.lib.geometry.Pose2dWithMotion;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.trajectory.Trajectory254;
import com.team254.lib.trajectory.timing.TimedState;
import com.team6647.frc2025.FieldLayout;
import com.team6647.frc2025.auto.actions.GenAction;
import com.team6647.frc2025.auto.actions.WaitForEnterPathGeneratedAction;
import com.team6647.frc2025.auto.paths.TrajectoryGenerator;
import com.team6647.frc2025.auto.paths.TrajectoryGenerator.TrajectorySet;
import com.team6647.frc2025.subsystems.Elevator;
import com.team6647.frc2025.subsystems.Superstructure;
import com.team6647.frc2025.subsystems.coral_roller.CoralRoller;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import java.util.List;

public class putCoral extends AutoModeBase {
	private Drive d = Drive.getInstance();
	private Superstructure s = Superstructure.getInstance();

	Trajectory254<TimedState<Pose2dWithMotion>> putCoral;
	public static Trajectory254<TimedState<Pose2dWithMotion>> enterCoral;

	public putCoral() {
		enterCoral = null;
	}

	// spotless:off
	@Override
	public void routine() throws AutoModeEndedException {
		CoralRoller.getInstance().setState(CoralRoller.State.CONSTANT);
		CoralPivot.getInstance().setSetpointMotionMagic(s.currentLevel.coralAngle);

		Pose2d endpose = s.getActiveCoral().toLegacy();
		if(s.level<4){
			endpose = FieldLayout.getCoralTargetPos(Superstructure.getInstance().angles[s.coralId]).realCorals[s.subCoralId].toLegacy();
		}else{
			endpose = FieldLayout.getCoralTargetPos(Superstructure.getInstance().angles[s.coralId]).corals4[s.subCoralId].toLegacy();
		}
		runAction(new SwervePIDAction(endpose));
		s.placing_coral = true;
		if(Superstructure.getInstance().level<4){
			s.request(
			new SequentialRequest(
				s.prepareLevel(s.currentLevel),
				new WaitRequest(0.2),
				new LambdaRequest(()->{CoralRoller.getInstance().setState(CoralRoller.getInstance().OUTAKING);}),
				new WaitRequest(0.5),
				new LambdaRequest(()->{CoralRoller.getInstance().setState(CoralRoller.State.IDLE);})
			)
			);
		}else{
			runAction(new WaitAction(0.3));
			s.request(
			new SequentialRequest(
				s.prepareLevel(s.currentLevel),
				new WaitRequest(0.6),
				new LambdaRequest(()->{CoralRoller.getInstance().setState(CoralRoller.getInstance().OUTAKING);}),
				new WaitRequest(0.5),
				new LambdaRequest(()->{CoralRoller.getInstance().setState(CoralRoller.State.IDLE);})
			)
			);
		}
		System.out.println("Finished auto!");
	}
	// spotless:on
}