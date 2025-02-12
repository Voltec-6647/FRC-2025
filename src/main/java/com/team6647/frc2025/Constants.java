package com.team6647.frc2025;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.team1678.frc2024.subsystems.servo.ServoMotorSubsystem;
import com.team1678.frc2024.subsystems.servo.ServoMotorSubsystem.ServoMotorSubsystemConstants;
import com.team1678.frc2024.subsystems.servo.ServoMotorSubsystem.TalonFXConstants;
import com.team1678.frc2024.subsystems.servo.ServoMotorSubsystemWithCancoder.AbsoluteEncoderConstants;
import com.team1678.lib.Conversions;
import com.team254.lib.drivers.CanDeviceId;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Constants {
    public static class OperatorConstants {

    }
    public static class DriveConstants {
        public static String swerveCANBus = "6647_Swerve";
		public static String mechanismsCANBus = "6647_CANivore";
        //public static String swerveCANBus = "rio";


    }

    public static class ElevatorConstants {
        public static final ServoMotorSubsystemConstants kElevatorServoConstants = new ServoMotorSubsystemConstants();

		static {
			kElevatorServoConstants.kName = "Elevator";

			kElevatorServoConstants.kMainConstants.counterClockwisePositive = false;
			kElevatorServoConstants.kMainConstants.invert_sensor_phase = false;

			kElevatorServoConstants.kFollowerConstants = new TalonFXConstants[1];
			kElevatorServoConstants.kFollowerConstants[0] = new TalonFXConstants();

			kElevatorServoConstants.kMainConstants.id = Ports.ELEVATOR_MAIN;
			kElevatorServoConstants.kFollowerConstants[0].id = Ports.ELEVATOR_FOLLOWER;

			kElevatorServoConstants.kHomePosition = 0.0; // meters

			kElevatorServoConstants.kMaxUnitsLimit = 0.46;
			kElevatorServoConstants.kMinUnitsLimit = 0.0;

			//14/20
			//81/20
			kElevatorServoConstants.kRotationsPerUnitDistance = (9.0) / (Conversions.inchesToMeters(1.432) * Math.PI);

			kElevatorServoConstants.kKp = 1.0; // Raw output / raw error
			kElevatorServoConstants.kKi = 0.0; // Raw output / sum of raw error
			kElevatorServoConstants.kKd = 0.0; // Raw output / (err - prevErr)
			kElevatorServoConstants.kKa = 0.0; // Raw output / accel in (rots/s) / s
			kElevatorServoConstants.kKg = 0.2;
			kElevatorServoConstants.kDeadband = 0; // rots

			kElevatorServoConstants.kCruiseVelocity = 1.0; // m / s
			kElevatorServoConstants.kAcceleration = 120.0; // m / s^2
			kElevatorServoConstants.kRampRate = 0.0; // s

			kElevatorServoConstants.kMaxForwardOutput = 12.0;
			kElevatorServoConstants.kMaxReverseOutput = -12.0;

			kElevatorServoConstants.kEnableSupplyCurrentLimit = true;
			kElevatorServoConstants.kSupplyCurrentLimit = 40; // amps
			kElevatorServoConstants.kSupplyCurrentThreshold = 40; // amps
			kElevatorServoConstants.kSupplyCurrentTimeout = 0.01; // seconds

			kElevatorServoConstants.kNeutralMode = NeutralModeValue.Brake;
		}

		public static double kHomingZone = 0.1; // meters
		public static double kHomingTimeout = 0.5; // seconds
		public static double kHomingVelocityWindow = 0.1; // "units" / second
		public static double kHomingOutput = -2.0; // volts

		//public static double kHomingZone = 0; // meters
		//public static double kHomingTimeout = 0.5; // seconds
		//public static double kHomingVelocityWindow = 0.1; // "units" / second
		//public static double kHomingOutput = -2.0; // volts
    }

	public static final class AlgaeRollerConstants {
		public static TalonFXConfiguration RollerFXConfig() {
			TalonFXConfiguration config = new TalonFXConfiguration();

			config.CurrentLimits.SupplyCurrentLimitEnable = true;
			config.CurrentLimits.SupplyCurrentLimit = 40.0;

			config.CurrentLimits.StatorCurrentLimitEnable = true;
			config.CurrentLimits.StatorCurrentLimit = 80.0;

			config.Voltage.PeakForwardVoltage = 12.0;
			config.Voltage.PeakReverseVoltage = -12.0;

			config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
			
			return config;
		}
	}

	public static final class AlgaeHolderConstants {
		public static SparkMaxConfig SparkMaxConfig() {
			SparkMaxConfig config = new SparkMaxConfig();

			config.smartCurrentLimit(30);

			//config.Voltage.PeakForwardVoltage = 12.0;
			//config.Voltage.PeakReverseVoltage = -12.0;

			config.idleMode(IdleMode.kBrake);
			return config;
		}
	}

	public static final class CoralPivotConstants {

		public static final double kRotorRotationsPerOutputRotation = 25.0 / 1.0; // Rotor to unit distance

		public static final ServoMotorSubsystemConstants kHoodServoConstants = new ServoMotorSubsystemConstants();
		public static final AbsoluteEncoderConstants kHoodEncoderConstants = new AbsoluteEncoderConstants();

		public static final double kHomingVoltage = -2.0;
		public static final double kHomingCurrentThreshold = 10.0;
		public static final double kMinHomingTime = 0.2;
		public static final double kMaxHomingTime = 4.0;

		static {
			kHoodServoConstants.kName = "CoralPivot";

			kHoodServoConstants.kMainConstants.id = Ports.CORAL_PIVOT;
			kHoodServoConstants.kMainConstants.counterClockwisePositive = true;

			kHoodServoConstants.kHomePosition = 0; // Degrees
			kHoodServoConstants.kTolerance = 1; // Degrees
			kHoodServoConstants.kRotationsPerUnitDistance = (1.0 / 360.0) /* (7.16 / 1.0)*/; // Cancoder to unit distance
			kHoodServoConstants.kKp = 30.0;
			kHoodServoConstants.kKi = 0;
			kHoodServoConstants.kKd = 0.0;
			kHoodServoConstants.kKg = 0;
			kHoodServoConstants.kKs = 0.0;
			kHoodServoConstants.kDeadband = 0; // Ticks

			//kHoodServoConstants.kMinUnitsLimit = 15.0;
			//kHoodServoConstants.kMaxUnitsLimit = 62.0;

			kHoodServoConstants.kCruiseVelocity = 200.0; // degrees / s
			kHoodServoConstants.kAcceleration = 20.0; // degrees / s^2

			kHoodServoConstants.kEnableSupplyCurrentLimit = true;
			kHoodServoConstants.kSupplyCurrentLimit = 40;
			kHoodServoConstants.kSupplyCurrentThreshold = 40;

			kHoodServoConstants.kEnableStatorCurrentLimit = true;
			kHoodServoConstants.kStatorCurrentLimit = 40;

			kHoodServoConstants.kMaxForwardOutput = 12.0;
			kHoodServoConstants.kMaxReverseOutput = -12.0;//12

			kHoodServoConstants.kRampRate = 0.0;

			kHoodServoConstants.kNeutralMode = NeutralModeValue.Coast;

			kHoodEncoderConstants.encoder_type = FeedbackSensorSourceValue.FusedCANcoder; //FusedCANcoder
			kHoodEncoderConstants.remote_encoder_port = Ports.CORAL_CANCODER;
			kHoodEncoderConstants.rotor_rotations_per_output = 0.0;
			kHoodEncoderConstants.remote_encoder_offset = 6.644531;
		}
	}

    
    
}
