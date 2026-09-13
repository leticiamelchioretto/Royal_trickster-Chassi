package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShutterSubsystem;

public class RobotContainer {

    private final DriveSubsystem drive = new DriveSubsystem();
    private final ShutterSubsystem shutter = new ShutterSubsystem();

    private final CommandXboxController controle = new CommandXboxController(0);

    public RobotContainer() {
        configurarComandosPadrao();
        configurarBotoes();
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

public Command getAutonomousCommand() {
    return Commands.sequence(

        Commands.run(
            () -> drive.andarRetoComPD(0.3),
            drive
        ).withTimeout(2),

        Commands.run(
            () -> shutter.ligarAmbos(),
            shutter
        ).withTimeout(2),

        Commands.runOnce(() -> {
            drive.stop();
            shutter.parar();
        })

    );
}
}