package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.DriveSubsystem;

public class AndarRetoComPDCommand extends Command {

    private final DriveSubsystem drive;
    private final double velocidade;

    public AndarRetoComPDCommand(DriveSubsystem drive, double velocidade) {
        this.drive = drive;
        this.velocidade = velocidade;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.resetEncoders();
    }

    @Override
    public void execute() {
        drive.andarRetoComPD(velocidade);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}