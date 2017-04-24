package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CaptureResult;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LockFocusCallbackTest {

	@Mock
	StageCallback.Result result;

	LockFocusCallback testee = new LockFocusCallback();

	@Test
	public void noAutoFocusState() throws Exception {
		// Given
		givenAutoFocusState(null);
		givenAutoExposureState(null);

		// When
		Stage stage = testee.processResult(result);

		// Then
		assertEquals(
				Stage.CAPTURE,
				stage
		);
	}

	@Test
	public void locked_Focused_NoAutoExposureState() throws Exception {
		// Given
		givenAutoFocusState(CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED);
		givenAutoExposureState(null);

		// When
		Stage stage = testee.processResult(result);

		// Then
		assertEquals(
				Stage.CAPTURE,
				stage
		);
	}

	@Test
	public void locked_Focused_AutoExposureInactive() throws Exception {
		// Given
		givenAutoFocusState(CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED);
		givenAutoExposureState(CaptureResult.CONTROL_AE_STATE_INACTIVE);

		// When
		Stage stage = testee.processResult(result);

		// Then
		assertEquals(
				Stage.CAPTURE,
				stage
		);
	}

	@Test
	public void locked_Focused_AutoExposureInProgress() throws Exception {
		// Given
		givenAutoFocusState(CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED);
		givenAutoExposureState(CaptureResult.CONTROL_AE_STATE_SEARCHING);

		// When
		Stage stage = testee.processResult(result);

		// Then
		assertEquals(
				Stage.PRECAPTURE,
				stage
		);
	}

	@Test
	public void focusIsOff() throws Exception {
		// Given
		givenAutoFocusState(CaptureResult.CONTROL_AF_STATE_INACTIVE);
		givenAutoExposureState(CaptureResult.CONTROL_AE_STATE_SEARCHING);

		// When
		Stage stage = testee.processResult(result);

		// Then
		assertEquals(
				Stage.CAPTURE,
				stage
		);
	}

	private void givenAutoExposureState(Integer state) {
		given(result.getAutoExposureState())
				.willReturn(state);
	}

	private void givenAutoFocusState(Integer state) {
		given(result.getAutoFocusState())
				.willReturn(state);
	}

}