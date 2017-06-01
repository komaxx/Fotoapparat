package io.fotoapparat.routine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.log.Logger;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Checks whether {@link LensPosition} provided by {@link SelectorFunction} is available or not.
 */
public class ChangeFlashModeRoutine {

    private final Logger logger;
    private final CameraDevice cameraDevice;

    public ChangeFlashModeRoutine(Logger logger, CameraDevice cameraDevice) {
        this.logger = logger;
        this.cameraDevice = cameraDevice;
    }

    public @Nullable Flash switchFlashMode(@NonNull Flash[] nuModePreferences) {
        if (nuModePreferences.length < 1){
            logger.log("Not switching flashes: No preferences given");
        }

        Set<Flash> flashes = cameraDevice.getCapabilities().supportedFlashModes();
        for (Flash candidate : nuModePreferences){
            if (flashes.contains(candidate)){
                Parameters parameters = new Parameters();
                parameters.putValue(Parameters.Type.FLASH, candidate);

                cameraDevice.updateParameters(parameters);
                return candidate;
            }
        }

        logger.log("Could not switch falsh mode: None supported.");
        return null;
    }

}
