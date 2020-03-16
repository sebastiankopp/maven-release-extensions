package de.sebastiankopp.maven.release.ext;

import org.apache.maven.shared.release.policy.version.VersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.apache.maven.shared.release.policy.version.VersionPolicyResult;
import org.codehaus.plexus.component.annotations.Component;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.removeEnd;

@Component(hint = "last-digit", role = VersionPolicy.class,
	description = "Always counts up the last 'number' within the version string")
public class LastDigitNamingStrategy implements VersionPolicy {
	
	public static final String SUFFIX_SNAPSHOT = "-SNAPSHOT";
	
	@Override
	public VersionPolicyResult getReleaseVersion(VersionPolicyRequest versionPolicyRequest) {
		final String releaseVersion = removeEnd(versionPolicyRequest.getVersion(), SUFFIX_SNAPSHOT);
		return new VersionPolicyResult().setVersion(releaseVersion);
	}
	
	@Override
	public VersionPolicyResult getDevelopmentVersion(VersionPolicyRequest versionPolicyRequest) {
		final String purgedVersion = removeEnd(versionPolicyRequest.getVersion(), SUFFIX_SNAPSHOT);
		final String[] numParts = purgedVersion.split("[^0-9]");
		final String lastNumPartString = lastElement(numParts);
		final String strSuffix = purgedVersion.matches(".*[0-9]+$") ?
				"" : lastElement(purgedVersion.split("[0-9]+"));
		final int increasedDigit = parseInt(lastNumPartString) + 1;
		final int prefixLength = purgedVersion.lastIndexOf(lastNumPartString);
		final String versStr = purgedVersion.substring(0, prefixLength) + increasedDigit + strSuffix + SUFFIX_SNAPSHOT;
		return new VersionPolicyResult().setVersion(versStr);
	}
	
	private <T> T lastElement(T[] arr) {
		return arr[arr.length-1];
	}
}
