package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * A {@link CaptureCallback} which will provide the resulting {@link Stage} after a auto focus
 * request has been performed.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class LockFocusCallback extends StageCallback {

	@Override
	public Stage processResult(Result result) {
		final Integer autoFocusState = result.getAutoFocusState();
		final Integer autoExposureState = result.getAutoExposureState();

		if (isAutoFocusPossible(autoFocusState)
				&& isAutoExposurePossible(autoExposureState)
				&& !isAutoExposureReady(autoExposureState)) {
			return Stage.PRECAPTURE;
		}

		return Stage.CAPTURE;
	}

	private boolean isAutoFocusPossible(@Nullable Integer state) {
		return state != null
				&& state != CaptureResult.CONTROL_AF_STATE_INACTIVE;
	}

	private boolean isAutoExposurePossible(@Nullable Integer state) {
		return state != null
				&& state != CaptureResult.CONTROL_AE_STATE_INACTIVE;
	}

	private boolean isAutoExposureReady(@Nullable Integer state) {
		return state != null
				&& state == CaptureResult.CONTROL_AE_STATE_CONVERGED;
	}

}
