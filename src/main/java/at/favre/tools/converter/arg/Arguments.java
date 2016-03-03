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


package at.favre.tools.converter.arg;

import at.favre.tools.converter.ConverterUtil;
import at.favre.tools.converter.ui.InvalidArgumentException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles all the arguments that can be set in the converter
 */
public class Arguments {
	public static final float DEFAULT_COMPRESSION_QUALITY = 0.9f;
	public static final int DEFAULT_THREAD_COUNT = 3;
	public static final RoundingHandler.Strategy DEFAULT_ROUNDING_STRATEGY = RoundingHandler.Strategy.ROUND_HALF_UP;
	public static final EPlatform DEFAULT_PLATFORM = EPlatform.ALL;


	public final static String[] VALID_FILE_EXTENSIONS = new String[]{ECompression.GIF.name().toLowerCase(), ECompression.JPG.name().toLowerCase(), ECompression.PNG.name().toLowerCase(), "jpeg"};

	public final static Arguments START_GUI = new Arguments();

	public final File src;
	public final File dst;
	public final float scrScale;
	public final EPlatform platform;
	public final EOutputCompressionMode outputCompressionMode;
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
	public final RoundingHandler.Strategy roundingHandler;
	public final List<File> filesToProcess;


	public Arguments(File src, File dst, float scrScale, EPlatform platform, EOutputCompressionMode outputCompressionMode,
	                 float compressionQuality, int threadCount, boolean skipExistingFiles, boolean skipUpscaling,
	                 boolean verboseLog, boolean includeAndroidLdpiTvdpi, boolean haltOnError, boolean createMipMapInsteadOfDrawableDir,
	                 boolean enablePngCrush, boolean postConvertWebp, boolean enableAntiAliasing, RoundingHandler.Strategy roundingHandler) {
		this.dst = dst;
		this.src = src;
		this.scrScale = scrScale;
		this.platform = platform;
		this.outputCompressionMode = outputCompressionMode;
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
		this.roundingHandler = roundingHandler;

		this.filesToProcess = new ArrayList<>();

		if (src != null && src.isDirectory()) {
			for (File file : src.listFiles()) {
				String extension = ConverterUtil.getFileExtension(file);
				if (Arrays.asList(VALID_FILE_EXTENSIONS).contains(extension)) {
					filesToProcess.add(file);
				}
			}
		} else {
			filesToProcess.add(src);
		}
	}

	private Arguments() {
		this(null, null, 0.27346f, null, null, 0.9362f, 996254, false, false, false, false, false, false, false, false, false, null);
	}

	public double round(double raw) {
		return new RoundingHandler(roundingHandler).round(raw);
	}

	@Override
	public String toString() {
		return "Arguments{" +
				"src=" + src +
				", dst=" + dst +
				", scrScale=" + scrScale +
				", platform=" + platform +
				", outputCompressionMode=" + outputCompressionMode +
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
				", roundingHandler=" + roundingHandler +
				", filesToProcess=" + filesToProcess +
				'}';
	}

	public static class Builder {
		private File dst;
		private float srcScale;
		private File src = null;
		private EPlatform platform = DEFAULT_PLATFORM;
		private EOutputCompressionMode outputCompressionMode = EOutputCompressionMode.SAME_AS_INPUT;
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

		public Builder(File src, float srcScale) {
			this.src = src;
			this.srcScale = srcScale;
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

			if (srcScale < 0 || srcScale > 100) {
				throw new InvalidArgumentException("invalid src scale given '" + srcScale + "' - must be between (excluding) 0 and 100");
			}

			return new Arguments(src, dst, srcScale, platform, outputCompressionMode, compressionQuality, threadCount, skipExistingFiles, skipUpscaling,
					verboseLog, includeAndroidLdpiTvdpi, haltOnError, createMipMapInsteadOfDrawableDir, enablePngCrush, postConvertWebp, enableAntiAliasing, roundingStrategy);
		}
	}

	public static ECompression getCompressionType(File srcFile) {
		String extension = ConverterUtil.getFileExtension(srcFile);
		switch (extension) {
			case "jpg":
			case "jpeg":
				return ECompression.JPG;
			case "png":
				return ECompression.PNG;
			case "gif":
				return ECompression.GIF;
			default:
				throw new IllegalArgumentException("unknown file extension " + extension + " in srcFile " + srcFile);
		}
	}

	public static List<ECompression> getCompressionForType(EOutputCompressionMode type, ECompression srcCompression) {
		List<ECompression> list = new ArrayList<>(2);
		switch (type) {
			case GIF:
				list.add(ECompression.GIF);
				break;
			case PNG:
				list.add(ECompression.PNG);
				break;
			case JPG:
				list.add(ECompression.JPG);
				break;
			case JPG_AND_PNG:
				list.add(ECompression.JPG);
				list.add(ECompression.PNG);
				break;
			default:
			case SAME_AS_INPUT:
				list.add(srcCompression);
				break;
		}
		return list;
	}
}
