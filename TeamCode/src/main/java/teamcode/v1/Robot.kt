package teamcode.v1

import com.asiankoala.koawalib.math.Pose
import com.asiankoala.koawalib.subsystem.drive.KMecanumDrive
import com.asiankoala.koawalib.subsystem.drive.KMecanumOdoDrive
import teamcode.v1.subsystems.Arm
import org.firstinspires.ftc.teamcode.koawalib.subsystems.Claw
import org.firstinspires.ftc.teamcode.koawalib.subsystems.Lift

class Robot(startPose: Pose) {
    val hardware = Hardware(startPose)

    val drive = KMecanumOdoDrive(
        hardware.fl,
        hardware.bl,
        hardware.br,
        hardware.fr,
        hardware.odometry,
        true
    )

    val arm = Arm(hardware.armMotor, hardware.limitSwitch)
    val claw = Claw(hardware.clawServo)
    val lift = Lift(hardware.liftLeadMotor, hardware.liftSecondMotor)

    init {
        arm.setPos(-71.0)
        lift.setPos(0.0)
    }
}