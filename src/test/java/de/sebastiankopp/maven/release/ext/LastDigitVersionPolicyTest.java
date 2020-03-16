package de.sebastiankopp.maven.release.ext;

import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LastDigitVersionPolicyTest {
	
	private final LastDigitVersionPolicy underTest = new LastDigitVersionPolicy();
	
	@Test(dataProvider = "dp")
	public void testGetReleaseVersion(String input, String expected, String _unused) {
		final String result = underTest.getReleaseVersion(new VersionPolicyRequest().setVersion(input))
				.getVersion();
		assertEquals(result, expected);
	}
	
	@Test(dataProvider = "dp")
	public void testGetDevelopmentVersion(String input, String _unused, String expected) {
		final String result = underTest.getDevelopmentVersion(new VersionPolicyRequest().setVersion(input))
				.getVersion();
		assertEquals(result, expected);
	}
	
	@DataProvider(name = "dp")
	static Object[][] dataProvider() {
		return new Object[][] {
		//  Input                 Expected Rel Vers	     Expected Dev Vers
			{"4.3.2.1-foo4-SNAPSHOT", "4.3.2.1-foo4", "4.3.2.1-foo5-SNAPSHOT" },
			{"4.3.2.1-foo-77-bar-wolf-SNAPSHOT", "4.3.2.1-foo-77-bar-wolf", "4.3.2.1-foo-78-bar-wolf-SNAPSHOT" },
			{"4.3.2.1-foo-2-bar-3-SNAPSHOT", "4.3.2.1-foo-2-bar-3", "4.3.2.1-foo-2-bar-4-SNAPSHOT" },
			{"4.3.2.1-SNAPSHOT", "4.3.2.1", "4.3.2.2-SNAPSHOT" },
			{"4.3.2.1", "4.3.2.1", "4.3.2.2-SNAPSHOT" },
			{"778", "778", "779-SNAPSHOT"}
		};
	}
}