package com.team6647.frc2025.auto.modes.configuredQuals;

import com.team1678.frc2024.auto.AutoModeBase;
import com.team1678.frc2024.auto.AutoModeEndedException;
import com.team1678.frc2024.auto.actions.GoCoralAction;
import com.team1678.frc2024.auto.actions.GoIntakeAction;
import com.team1678.frc2024.auto.actions.PathplannerPathAction;
import com.team1678.frc2024.auto.actions.RequestAction;
import com.team1678.frc2024.auto.actions.ResetGyroAction;
import com.team1678.frc2024.auto.actions.ResetOdometryAction;
import com.team1678.frc2024.auto.actions.WaitAction;
import com.team1678.frc2024.subsystems.CoralPivot;
import com.team1678.frc2024.subsystems.Drive;
import com.team1678.lib.requests.DepositCoralRequest;
import com.team1678.lib.requests.IntakeCoralRequest;
import com.team6647.frc2025.subsystems.Superstructure;
import com.team6647.frc2025.subsystems.vision.VisionSubsystem;

public class A5R2PPPID extends AutoModeBase {
	private Drive d = Drive.getInstance();
	private Superstructure s = Superstructure.getInstance();
	private VisionSubsystem v = VisionSubsystem.getInstance();
	public A5R2PPPID() {
		
	}

	// spotless:off
	@Override
	protected void routine() throws AutoModeEndedException {
		runAction(new WaitAction(0.6));
		runAction(new ResetOdometryAction("5R1"));
		s.setLevel(4);
		s.coralId = 5;
		s.subCoralId = 1;
		runAction(new GoCoralAction());
		runAction(new RequestAction(DepositCoralRequest.get()));
		
		s.setLevel(4);
		s.coralId = 4;
		s.subCoralId = 1;
		CoralPivot.getInstance().setSetpointMotionMagic(CoralPivot.kIntakingAngle);
		runAction(new PathplannerPathAction("5R2"));
		runAction(new GoIntakeAction());
		runAction(new RequestAction(IntakeCoralRequest.get()));

		runAction(new PathplannerPathAction("5R3"));
		runAction(new WaitAction(0.2));
		runAction(new ResetGyroAction());
		runAction(new WaitAction(0.1));
		runAction(new GoCoralAction());
		runAction(new RequestAction(DepositCoralRequest.get()));




		System.out.println("Finished auto!");
	}
	// spotless:on
}
