package io.fotoapparat.hardware.v2.captor;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * A {@link CaptureCallback} which will provide the resulting {@link Stage} after a auto exposure
 * (precapture) request has been performed.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class PrecaptureCallback extends StageCallback {

	@Override
	Stage processResult(Result result) {
		return Stage.CAPTURE;
	}

}
