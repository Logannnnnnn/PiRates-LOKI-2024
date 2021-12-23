package org.firstinspires.ftc.teamcode.commands.carousel;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.carousel.CarouselSubsystem;

public class StopCarousel extends CommandBase {

    private final CarouselSubsystem carouselSubsytem;

    private final Double power = 0.0;

    private Telemetry telemetry;


    public StopCarousel(CarouselSubsystem subsystem){
        carouselSubsytem = subsystem;
        addRequirements(subsystem);
    }

    public StopCarousel(CarouselSubsystem subsystem, Telemetry telemetry){
        carouselSubsytem = subsystem;
        this.telemetry = telemetry;

        addRequirements(subsystem);
    }


    @Override
    public void initialize(){
        carouselSubsytem.setPower(power);
    }

    @Override
    public boolean isFinished(){
        telemetry.addData("stop carousel","isFinished");
        return true;
    }

}
