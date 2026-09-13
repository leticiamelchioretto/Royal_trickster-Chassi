package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShutterSubsystem extends SubsystemBase {

    private final SparkMax superior;
    private final SparkMax inferior;

    public ShutterSubsystem() {
    superior = new SparkMax(55, MotorType.kBrushed);
    inferior = new SparkMax(44, MotorType.kBrushed);
    }

    public void ligarSuperior()    { superior.set(0.8); }
    public void desligarSuperior() { superior.set(0); }

    public void ligarInferior()    { inferior.set(0.8); }
    public void desligarInferior() { inferior.set(0); }

    public void ligarAmbos() {
        superior.set(0.8);
        inferior.set(0.8);
    }

    public void parar() {
        superior.set(0);
        inferior.set(0);
    }
}