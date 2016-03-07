/*
 * Copyright (C) 2016 Patrick Favre-Bulle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package at.favre.tools.dconvert.arg;

import at.favre.tools.dconvert.ui.InvalidArgumentException;
import at.favre.tools.dconvert.util.MiscUtil;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Handles all the arguments that can be set in the dconvert
 */
public class Arguments implements Serializable {
	private static final long serialVersionUID = 3;

	public static final float DEFAULT_SCALE = 3f;
	public static final float DEFAULT_COMPRESSION_QUALITY = 0.9f;
	public static final int DEFAULT_THREAD_COUNT = 4;
	public static final RoundingHandler.Strategy DEFAULT_ROUNDING_STRATEGY = RoundingHandler.Strategy.ROUND_HALF_UP;
	public static final EPlatform DEFAULT_PLATFORM = EPlatform.ALL;
	public static final EOutputCompressionMode DEFAULT_OUT_COMPRESSION = EOutputCompressionMode.SAME_AS_INPUT_PREF_PNG;
	public static final EScaleType DEFAULT_SCALE_TYPE = EScaleType.FACTOR;


	public final static Arguments START_GUI = new Arguments(null, null, 0.27346f, null, null, null, 0.9362f, 996254, false, false, false, false, false, false, false, false, false, false, null);

	public final File src;
	public final File dst;
	public final float scale;
	public final EPlatform platform;
	public final EOutputCompressionMode outputCompressionMode;
	public final EScaleType scaleType;
	public final float compressionQuality;
	public final int threadCount;
	public final boolean skipExistingFiles;
	public final boolean skipUpscaling;
	public final boolean verboseLog;
	public final boolean includeAndroidLdpiTvdpi;
	public final boolean haltOnError;
	public final boolean createMipMapInsteadOfDrawableDir;
	public final boolean enablePngCrush;
	public final boolean postConvertWebp;
	public final boolean enableAntiAliasing;
	public final boolean dryRun;
	public final RoundingHandler.Strategy roundingHandler;
	public transient final List<File> filesToProcess;


	public Arguments(File src, File dst, float scale, EPlatform platform, EOutputCompressionMode outputCompressionMode,
	                 EScaleType scaleType, float compressionQuality, int threadCount, boolean skipExistingFiles, boolean skipUpscaling,
	                 boolean verboseLog, boolean includeAndroidLdpiTvdpi, boolean haltOnError, boolean createMipMapInsteadOfDrawableDir,
	                 boolean enablePngCrush, boolean postConvertWebp, boolean enableAntiAliasing, boolean dryRun, RoundingHandler.Strategy roundingHandler) {
		this.dst = dst;
		this.src = src;
		this.scale = scale;
		this.platform = platform;
		this.outputCompressionMode = outputCompressionMode;
		this.scaleType = scaleType;
		this.compressionQuality = compressionQuality;
		this.threadCount = threadCount;
		this.skipExistingFiles = skipExistingFiles;
		this.skipUpscaling = skipUpscaling;
		this.verboseLog = verboseLog;
		this.includeAndroidLdpiTvdpi = includeAndroidLdpiTvdpi;
		this.haltOnError = haltOnError;
		this.createMipMapInsteadOfDrawableDir = createMipMapInsteadOfDrawableDir;
		this.enablePngCrush = enablePngCrush;
		this.postConvertWebp = postConvertWebp;
		this.enableAntiAliasing = enableAntiAliasing;
		this.dryRun = dryRun;
		this.roundingHandler = roundingHandler;

		this.filesToProcess = new ArrayList<>();

		Set<String> supportedFileTypes = getSupportedFileTypes();

		if (src != null && src.isDirectory()) {
			for (File file : src.listFiles()) {
				String extension = MiscUtil.getFileExtensionLowerCase(file);
				if (supportedFileTypes.contains(extension)) {
					filesToProcess.add(file);
				}
			}
		} else {
			if (supportedFileTypes.contains(MiscUtil.getFileExtensionLowerCase(src))) {
				filesToProcess.add(src);
			}
		}
	}

	public Arguments() {
		this(null, null, DEFAULT_SCALE, DEFAULT_PLATFORM, DEFAULT_OUT_COMPRESSION, DEFAULT_SCALE_TYPE, DEFAULT_COMPRESSION_QUALITY, DEFAULT_THREAD_COUNT,
				false, false, true, false, false, false, false, false, false, false, DEFAULT_ROUNDING_STRATEGY);
	}

	public double round(double raw) {
		return new RoundingHandler(roundingHandler).round(raw);
	}

	@Override
	public String toString() {
		return "Arguments{" +
				"src=" + src +
				", dst=" + dst +
				", scale=" + scale +
				", platform=" + platform +
				", outputCompressionMode=" + outputCompressionMode +
				", scaleType=" + scaleType +
				", compressionQuality=" + compressionQuality +
				", threadCount=" + threadCount +
				", skipExistingFiles=" + skipExistingFiles +
				", skipUpscaling=" + skipUpscaling +
				", verboseLog=" + verboseLog +
				", includeAndroidLdpiTvdpi=" + includeAndroidLdpiTvdpi +
				", haltOnError=" + haltOnError +
				", createMipMapInsteadOfDrawableDir=" + createMipMapInsteadOfDrawableDir +
				", enablePngCrush=" + enablePngCrush +
				", postConvertWebp=" + postConvertWebp +
				", enableAntiAliasing=" + enableAntiAliasing +
				", dryRun=" + dryRun +
				", roundingHandler=" + roundingHandler +
				", filesToProcess=" + filesToProcess +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Arguments arguments = (Arguments) o;

		if (Float.compare(arguments.scale, scale) != 0) return false;
		if (Float.compare(arguments.compressionQuality, compressionQuality) != 0) return false;
		if (threadCount != arguments.threadCount) return false;
		if (skipExistingFiles != arguments.skipExistingFiles) return false;
		if (skipUpscaling != arguments.skipUpscaling) return false;
		if (verboseLog != arguments.verboseLog) return false;
		if (includeAndroidLdpiTvdpi != arguments.includeAndroidLdpiTvdpi) return false;
		if (haltOnError != arguments.haltOnError) return false;
		if (createMipMapInsteadOfDrawableDir != arguments.createMipMapInsteadOfDrawableDir) return false;
		if (enablePngCrush != arguments.enablePngCrush) return false;
		if (postConvertWebp != arguments.postConvertWebp) return false;
		if (enableAntiAliasing != arguments.enableAntiAliasing) return false;
		if (dryRun != arguments.dryRun) return false;
		if (src != null ? !src.equals(arguments.src) : arguments.src != null) return false;
		if (dst != null ? !dst.equals(arguments.dst) : arguments.dst != null) return false;
		if (platform != arguments.platform) return false;
		if (outputCompressionMode != arguments.outputCompressionMode) return false;
		if (scaleType != arguments.scaleType) return false;
		if (roundingHandler != arguments.roundingHandler) return false;
		return filesToProcess != null ? filesToProcess.equals(arguments.filesToProcess) : arguments.filesToProcess == null;

	}

	@Override
	public int hashCode() {
		int result = src != null ? src.hashCode() : 0;
		result = 31 * result + (dst != null ? dst.hashCode() : 0);
		result = 31 * result + (scale != +0.0f ? Float.floatToIntBits(scale) : 0);
		result = 31 * result + (platform != null ? platform.hashCode() : 0);
		result = 31 * result + (outputCompressionMode != null ? outputCompressionMode.hashCode() : 0);
		result = 31 * result + (scaleType != null ? scaleType.hashCode() : 0);
		result = 31 * result + (compressionQuality != +0.0f ? Float.floatToIntBits(compressionQuality) : 0);
		result = 31 * result + threadCount;
		result = 31 * result + (skipExistingFiles ? 1 : 0);
		result = 31 * result + (skipUpscaling ? 1 : 0);
		result = 31 * result + (verboseLog ? 1 : 0);
		result = 31 * result + (includeAndroidLdpiTvdpi ? 1 : 0);
		result = 31 * result + (haltOnError ? 1 : 0);
		result = 31 * result + (createMipMapInsteadOfDrawableDir ? 1 : 0);
		result = 31 * result + (enablePngCrush ? 1 : 0);
		result = 31 * result + (postConvertWebp ? 1 : 0);
		result = 31 * result + (enableAntiAliasing ? 1 : 0);
		result = 31 * result + (dryRun ? 1 : 0);
		result = 31 * result + (roundingHandler != null ? roundingHandler.hashCode() : 0);
		result = 31 * result + (filesToProcess != null ? filesToProcess.hashCode() : 0);
		return result;
	}

	public static Set<String> getSupportedFileTypes() {
		Set<String> set = new HashSet<>();
		for (ImageType imageType : ImageType.values()) {
			if (imageType.supportRead) {
				set.addAll(Arrays.asList(imageType.extensions));
			}
		}

		return set;
	}

	public static class Builder {
		private File dst;
		private float srcScale;
		private File src = null;
		private EScaleType scaleType = DEFAULT_SCALE_TYPE;
		private EPlatform platform = DEFAULT_PLATFORM;
		private EOutputCompressionMode outputCompressionMode = DEFAULT_OUT_COMPRESSION;
		private float compressionQuality = DEFAULT_COMPRESSION_QUALITY;
		private int threadCount = DEFAULT_THREAD_COUNT;
		private RoundingHandler.Strategy roundingStrategy = DEFAULT_ROUNDING_STRATEGY;
		private boolean skipExistingFiles = false;
		private boolean skipUpscaling = false;
		private boolean verboseLog = false;
		private boolean includeAndroidLdpiTvdpi = false;
		private boolean haltOnError = false;
		private boolean createMipMapInsteadOfDrawableDir = false;
		private boolean enableAntiAliasing = false;
		private boolean enablePngCrush = false;
		private boolean postConvertWebp = false;
		private boolean dryRun;

		public Builder(File src, float srcScale) {
			this.src = src;
			this.srcScale = srcScale;
		}

		public Builder scaleType(EScaleType type) {
			this.scaleType = type;
			return this;
		}

		public Builder dstFolder(File dst) {
			this.dst = dst;
			return this;
		}

		public Builder platform(EPlatform platform) {
			this.platform = platform;
			return this;
		}

		public Builder compression(EOutputCompressionMode outputCompressionMode) {
			this.outputCompressionMode = outputCompressionMode;
			return this;
		}

		public Builder compression(EOutputCompressionMode outputCompressionMode, float compressionQuality) {
			this.outputCompressionMode = outputCompressionMode;
			this.compressionQuality = compressionQuality;
			return this;
		}

		public Builder threadCount(int threadCount) {
			this.threadCount = threadCount;
			return this;
		}

		public Builder skipExistingFiles(boolean b) {
			this.skipExistingFiles = b;
			return this;
		}

		public Builder skipUpscaling(boolean b) {
			this.skipUpscaling = b;
			return this;
		}

		public Builder verboseLog(boolean b) {
			this.verboseLog = b;
			return this;
		}

		public Builder includeAndroidLdpiTvdpi(boolean b) {
			this.includeAndroidLdpiTvdpi = b;
			return this;
		}

		public Builder haltOnError(boolean b) {
			this.haltOnError = b;
			return this;
		}

		public Builder createMipMapInsteadOfDrawableDir(boolean b) {
			this.createMipMapInsteadOfDrawableDir = b;
			return this;
		}

		public Builder antiAliasing(boolean b) {
			this.enableAntiAliasing = b;
			return this;
		}

		public Builder enablePngCrush(boolean b) {
			this.enablePngCrush = b;
			return this;
		}

		public Builder postConvertWebp(boolean b) {
			this.postConvertWebp = b;
			return this;
		}

		public Builder dryRun(boolean b) {
			this.dryRun = b;
			return this;
		}

		public Builder scaleRoundingStragy(RoundingHandler.Strategy strategy) {
			this.roundingStrategy = strategy;
			return this;
		}

		public Arguments build() throws InvalidArgumentException {
			if (src == null || !src.exists()) {
				throw new InvalidArgumentException("src file/directory must be passed and should exist: " + src);
			}

			if (dst == null) {
				if (src.isDirectory()) {
					dst = src;
				} else {
					dst = src.getParentFile();
				}
			}

			if (compressionQuality < 0 || compressionQuality > 1.0) {
				throw new InvalidArgumentException("invalid compression quality argument '" + compressionQuality + "' - must be between (including) 0 and 1.0");
			}

			if (threadCount < 1 || threadCount > 8) {
				throw new InvalidArgumentException("invalid thread count given '" + threadCount + "' - must be between (including) 1 and 8");
			}

			switch (scaleType) {
				case FACTOR:
					if (srcScale <= 0 || srcScale >= 100) {
						throw new InvalidArgumentException("invalid src scale factor given '" + srcScale + "' - must be between (excluding) 0.0 and 100");
					}
					break;
				case DP_WIDTH:
				case DP_HEIGHT:
					if (srcScale <= 0 || srcScale >= 9999) {
						throw new InvalidArgumentException("invalid src scale dp given '" + srcScale + "' - must be between 1dp and 9999dp");
					}
					break;
			}

			return new Arguments(src, dst, srcScale, platform, outputCompressionMode, scaleType, compressionQuality, threadCount, skipExistingFiles, skipUpscaling,
					verboseLog, includeAndroidLdpiTvdpi, haltOnError, createMipMapInsteadOfDrawableDir, enablePngCrush, postConvertWebp, enableAntiAliasing, dryRun, roundingStrategy);
		}
	}

	public static ImageType getImageType(File srcFile) {
		String extension = MiscUtil.getFileExtensionLowerCase(srcFile);
		switch (extension) {
			case "jpg":
			case "jpeg":
				return ImageType.JPG;
			case "png":
				return ImageType.PNG;
			case "svg":
				return ImageType.SVG;
			case "tif":
			case "tiff":
				return ImageType.TIFF;
			case "psd":
				return ImageType.PSD;
			case "gif":
				return ImageType.GIF;
			case "bmp":
				return ImageType.BMP;
			default:
				throw new IllegalArgumentException("unknown file extension " + extension + " in srcFile " + srcFile);
		}
	}

	public static List<ImageType.ECompression> getOutCompressionForType(EOutputCompressionMode type, ImageType imageType) {
		List<ImageType.ECompression> list = new ArrayList<>(2);
		switch (type) {
			case AS_GIF:
				list.add(ImageType.ECompression.GIF);
				break;
			case AS_PNG:
				list.add(ImageType.ECompression.PNG);
				break;
			case AS_JPG:
				list.add(ImageType.ECompression.JPG);
				break;
			case AS_JPG_AND_PNG:
				list.add(ImageType.ECompression.JPG);
				list.add(ImageType.ECompression.PNG);
				break;
			case AS_BMP:
				list.add(ImageType.ECompression.BMP);
				break;
			case SAME_AS_INPUT_PREF_PNG:
				list.add(imageType.outCompressionCompat);
				break;
			default:
			case SAME_AS_INPUT_STRICT:
				list.add(imageType.outCompressionStrict);
				break;
		}
		return list;
	}
}