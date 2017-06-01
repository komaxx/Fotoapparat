package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.selector.SelectorFunction;

/**
 * Checks whether {@link LensPosition} provided by {@link SelectorFunction} is available or not.
 */
public class ChangeFlashModeRoutine {

    private final CameraDevice cameraDevice;

    public ChangeFlashModeRoutine(CameraDevice cameraDevice) {
        this.cameraDevice = cameraDevice;
    }

    public void switchFlashMode(Flash nuMode) {
        Parameters parameters = new Parameters();
        parameters.putValue(Parameters.Type.FLASH, nuMode);

        cameraDevice.updateParameters(parameters);
    }

}
