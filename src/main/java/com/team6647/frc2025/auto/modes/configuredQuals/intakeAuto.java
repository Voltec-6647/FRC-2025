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
import com.team1678.lib.requests.WaitForPrereqRequest;
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

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import java.util.List;

public class intakeAuto extends AutoModeBase {
	private Drive d = Drive.getInstance();
	private Superstructure s = Superstructure.getInstance();

	public intakeAuto() {
	}

	// spotless:off
	@Override
	protected void routine() throws AutoModeEndedException {
		//CoralRoller.getInstance().setState(CoralRoller.State.INTAKING);
		CoralPivot.getInstance().setSetpointMotionMagic(CoralPivot.kIntakingAngle);
		CoralRoller mCoralRoller = CoralRoller.getInstance();
		s.request(
			new SequentialRequest(
				new LambdaRequest(
					()->{mCoralRoller.setState(CoralRoller.State.INTAKING);}
				),
				new WaitRequest(0.2),
				new WaitForPrereqRequest(()->mCoralRoller.getBeamBreak()),
				new LambdaRequest(
					()->{mCoralRoller.setState(CoralRoller.State.IDLE);}
				)
			)
			);
		//Drive.getInstance().setControlState(DriveControlState.HEADING_CONTROL);
		//runAction(new TurnInPlaceAction(Superstructure.getInstance().getActiveCoral().getRotation(), 0.6));
		//runAction(new SwerveTrajectoryAction(putCoral, false));
		runAction(new SwervePIDAction(s.sourcePose.toLegacy()));

		//runAction(new WaitAction(1));
		
		
 
		
		
		/*
		runAction(new RequestAction(s.prepareLevel(s.currentLevel)));
		runAction(new LambdaAction(()->{
			CoralRoller.getInstance().setState(CoralRoller.getInstance().OUTAKING);
		}));
		 */
		
		//el level
		//runAction(new WaitForEnterPathGeneratedAction());
		//runAction(new SwerveTrajectoryAction(enterCoral, false));
		//spin thing

		System.out.println("Finished auto!");
	}
	// spotless:on
}