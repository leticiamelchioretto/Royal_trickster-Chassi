package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkRelativeEncoder;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase  {
    private SparkMax frontLeft, backLeft, frontRight, backRight;
    private SparkRelativeEncoder encoderEsquerdo, encoderDireito;

    private static final double Kp = 0.03;
    private static final double Kd = 0.01;
    private static final double VELOCIDADE_BASE = 0.3;
    private double erroAnterior = 0;

    public DriveSubsystem() {
        frontLeft  = new SparkMax(6, MotorType.kBrushed);
        backLeft   = new SparkMax(7, MotorType.kBrushed);
        frontRight = new SparkMax(4, MotorType.kBrushed);
        backRight  = new SparkMax(5, MotorType.kBrushed);

        encoderEsquerdo = (SparkRelativeEncoder) frontLeft.getEncoder();
        encoderDireito  = (SparkRelativeEncoder) frontRight.getEncoder();
    }
        public void acionarMotores(double vEsquerda, double vDireita) {
    frontLeft.set(vEsquerda);
    backLeft.set(vEsquerda);
    frontRight.set(vDireita);
    backRight.set(vDireita);
    }

    public void arcadeDrive(double velocidade, double rotacao) {
        double esquerdo = velocidade + rotacao;
        double direito  = -(velocidade - rotacao);
        acionarMotores(esquerdo, direito);
    }

    public void andarRetoComPD(double velocidadeBase) {
        double posEsquerda = encoderEsquerdo.getPosition();
        double posDireita  = encoderDireito.getPosition();
        double erro = posEsquerda - posDireita;

        double derivativa = erro - erroAnterior;
        erroAnterior = erro;

        double correcao = (Kp * erro) + (Kd * derivativa);

        double vEsquerda =  velocidadeBase - correcao;
        double vDireita  = -(velocidadeBase + correcao);

        acionarMotores(vEsquerda, vDireita);
    }

    public void resetEncoders() {
        encoderEsquerdo.setPosition(0);
        encoderDireito.setPosition(0);
        erroAnterior = 0;
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
