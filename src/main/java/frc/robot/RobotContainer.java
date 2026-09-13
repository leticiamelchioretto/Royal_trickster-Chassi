package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShutterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {

    private final DriveSubsystem drive = new DriveSubsystem();
    private final ShutterSubsystem shutter = new ShutterSubsystem();

    private final CommandXboxController controle = new CommandXboxController(0);

    private final SendableChooser<Command> autoChooser = new SendableChooser<>();


    public RobotContainer() {
        configurarComandosPadrao();
        configurarBotoes();
        configurarAutonomos();
    }

    private void configurarComandosPadrao() {
        drive.setDefaultCommand(new RunCommand(
            () -> drive.arcadeDrive(
                -controle.getLeftY(),
                 controle.getRightX()
            ),
            drive
        ));
    }

    private void configurarBotoes() {
        controle.a().whileTrue(Commands.startEnd(
            shutter::ligarSuperior,
            shutter::desligarSuperior,
            shutter
        ));

        controle.x().whileTrue(Commands.startEnd(
            shutter::ligarInferior,
            shutter::desligarInferior,
            shutter
        ));
    }


    private void configurarAutonomos() {

        autoChooser.setDefaultOption("Nada", Commands.none());

        autoChooser.addOption("Só anda",
            Commands.run(() -> drive.andarRetoComPD(0.3), drive).withTimeout(2)
        );

        autoChooser.addOption("Anda + shutters",
            Commands.sequence(
                Commands.run(() -> drive.andarRetoComPD(0.3), drive).withTimeout(2),
                Commands.run(() -> shutter.ligarAmbos(), shutter).withTimeout(2),
                Commands.runOnce(() -> { drive.stop(); shutter.parar(); })
            )
        );

        SmartDashboard.putData("Autônomo", autoChooser);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
    }