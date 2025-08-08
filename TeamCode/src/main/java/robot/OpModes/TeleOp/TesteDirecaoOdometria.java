package robot.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Teste Direção Odometria", group="Testes")
public class TesteDirecaoOdometria extends LinearOpMode {

    private DcMotor leftEncoder;
    private DcMotor rightEncoder;
    private DcMotor strafeEncoder;

    // Variáveis para armazenar posições anteriores
    private int lastLeftPos = 0;
    private int lastRightPos = 0;
    private int lastStrafePos = 0;

    @Override
    public void runOpMode() {
        // Inicializa os encoders (substitua pelos nomes do seu hardware)
        leftEncoder = hardwareMap.get(DcMotor.class, "BLmotor");
        rightEncoder = hardwareMap.get(DcMotor.class, "BRmotor");
        strafeEncoder = hardwareMap.get(DcMotor.class, "FRmotor");

        // Configura os encoders para modo encoder
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        strafeEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        strafeEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // Lê as posições atuais
            int currentLeftPos = leftEncoder.getCurrentPosition();
            int currentRightPos = rightEncoder.getCurrentPosition();
            int currentStrafePos = strafeEncoder.getCurrentPosition();

            lastLeftPos = currentLeftPos;
            lastRightPos = currentRightPos;
            lastStrafePos = currentStrafePos;

            // Calcula as mudanças desde a última leitura
            int deltaLeft = currentLeftPos - lastLeftPos;
            int deltaRight = currentRightPos - lastRightPos;
            int deltaStrafe = currentStrafePos - lastStrafePos;

            // Atualiza as posições anteriores


            // Exibe informações no telemetry


            telemetry.addData("Left Encoder (BLmotor)", currentLeftPos);
            telemetry.addData("Right Encoder (BRmotor)", currentRightPos);
            telemetry.addData("Strafe Encoder (FRmotor)", currentStrafePos);
            telemetry.addLine("");

            telemetry.addData("Delta Left", deltaLeft);
            telemetry.addData("Delta Right", deltaRight);
            telemetry.addData("Delta Strafe", deltaStrafe);
            telemetry.addLine("");

            telemetry.addLine("Direções atuais configuradas:");
            telemetry.addData("Left Direction", leftEncoder.getDirection());
            telemetry.addData("Right Direction", rightEncoder.getDirection());
            telemetry.addData("Strafe Direction", strafeEncoder.getDirection());

            telemetry.update();

            sleep(50); // Pequeno delay para evitar sobrecarga
        }
    }
}