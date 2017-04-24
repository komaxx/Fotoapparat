package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

/**
 * A {@link CaptureCallback} which will provide the current {@link Stage} of the photo capturing
 * routine upon this capturing request has finished.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
abstract class StageCallback extends CameraCaptureSession.CaptureCallback {

	private CountDownLatch countDownLatch = new CountDownLatch(1);
	private Stage stage;

	@Override
	public void onCaptureCompleted(@NonNull CameraCaptureSession session,
								   @NonNull CaptureRequest request,
								   @NonNull TotalCaptureResult result) {
		super.onCaptureCompleted(session, request, result);
		this.stage = processResult(new Result(result));
		countDownLatch.countDown();
	}

	/**
	 * Provides the capturing routine Stage when it has been processed.
	 *
	 * @return The current capturing stage.
	 */
	Stage getCaptureStage() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return stage;
	}

	/**
	 * Provides the capturing routine Stage upon processing the result of a capturing request.
	 *
	 * @param result The subset of the results of a single image capture from the image sensor.
	 * @return The current capturing stage.
	 */
	abstract Stage processResult(Result result);

	/**
	 * This class does not serve any special purpose apart from wrapping {@link CaptureResult} so
	 * that it becomes testable.
	 */
	static class Result {

		private final CaptureResult captureResult;

		Result(CaptureResult captureResult) {
			this.captureResult = captureResult;
		}

		/**
		 * @see CaptureResult#CONTROL_AF_STATE
		 */
		@Nullable
		public Integer getAutoFocusState() {
			return captureResult.get(CaptureResult.CONTROL_AF_STATE);
		}

		/**
		 * @see CaptureResult#CONTROL_AE_STATE
		 */
		@Nullable
		public Integer getAutoExposureState() {
			return captureResult.get(CaptureResult.CONTROL_AE_STATE);
		}

	}

}
