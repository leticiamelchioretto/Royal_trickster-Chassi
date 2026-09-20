package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkRelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase  {
    private SparkMax frontLeft, backLeft, frontRight, backRight;
    private SparkRelativeEncoder encoderEsquerdo, encoderDireito;

    private final PIDController pid = new PIDController(0.03, 0.0005, 0.01);

    public DriveSubsystem() {
        frontLeft  = new SparkMax(31, MotorType.kBrushed);
        backLeft   = new SparkMax(32, MotorType.kBrushed);
        frontRight = new SparkMax(4, MotorType.kBrushed);
        backRight  = new SparkMax(30, MotorType.kBrushed);

        encoderEsquerdo = (SparkRelativeEncoder) frontLeft.getEncoder();
        encoderDireito  = (SparkRelativeEncoder) frontRight.getEncoder();
        
        pid.setIntegratorRange(-0.5, 0.5);
    }
    public void acionarMotores(double vEsquerda, double vDireita) {
        frontLeft.set(vEsquerda);
        backLeft.set(vEsquerda);
        frontRight.set(vDireita);
        backRight.set(vDireita);
    }

    public void arcadeDrive(double velocidade, double rotacao) {
        double speedLimit = 0.3;
        double esquerdo = (velocidade * speedLimit) + (rotacao * speedLimit);
        double direito  = -((velocidade * speedLimit) - (rotacao * speedLimit));
    }

    public void andarRetoComPD(double velocidadeBase) {
    double posEsquerda = encoderEsquerdo.getPosition();
    double posDireita  = encoderDireito.getPosition();
    double erro = posEsquerda - posDireita;

    double correcao = pid.calculate(erro, 0);

    double vEsquerda =  velocidadeBase - correcao;
    double vDireita  = -(velocidadeBase + correcao);

    acionarMotores(vEsquerda, vDireita);
    }

    public void resetEncoders() {
        encoderEsquerdo.setPosition(0);
        encoderDireito.setPosition(0);
        pid.reset();
    }

    public void stop() {
        acionarMotores(0, 0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Drive/Encoder Esquerdo", encoderEsquerdo.getPosition());
        SmartDashboard.putNumber("Drive/Encoder Direito", encoderDireito.getPosition());
        SmartDashboard.putNumber("Drive/Velocidade FL", frontLeft.get());
        SmartDashboard.putNumber("Drive/Velocidade FR", frontRight.get());
        SmartDashboard.putNumber("Drive/Velocidade BL", backLeft.get());
        SmartDashboard.putNumber("Drive/Velocidade BR", backRight.get());
    }
}
