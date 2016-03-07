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

package at.favre.tools.dconvert.converters;

import at.favre.tools.dconvert.arg.Arguments;
import at.favre.tools.dconvert.arg.EPlatform;
import at.favre.tools.dconvert.arg.ImageType;
import at.favre.tools.dconvert.converters.descriptors.AndroidDensityDescriptor;
import at.favre.tools.dconvert.util.MiscUtil;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts and creates Android-style resource set
 */
public class AndroidConverter extends APlatformConverter<AndroidDensityDescriptor> {

	@Override
	public List<AndroidDensityDescriptor> usedOutputDensities(Arguments arguments) {
		return getAndroidDensityDescriptors(arguments);
	}

	public static List<AndroidDensityDescriptor> getAndroidDensityDescriptors(Arguments arguments) {
		List<AndroidDensityDescriptor> list = new ArrayList<>();
		if (arguments.includeAndroidLdpiTvdpi) {
			list.add(new AndroidDensityDescriptor(0.75f, "ldpi", "drawable-ldpi"));
			list.add(new AndroidDensityDescriptor(1.33f, "tvdpi", "drawable-tvdpi"));
		}
		list.add(new AndroidDensityDescriptor(1, "mdpi", "drawable-mdpi"));
		list.add(new AndroidDensityDescriptor(1.5f, "hdpi", "drawable-hdpi"));
		list.add(new AndroidDensityDescriptor(2, "xhdpi", "drawable-xhdpi"));
		list.add(new AndroidDensityDescriptor(3, "xxhdpi", "drawable-xxhdpi"));
		list.add(new AndroidDensityDescriptor(4, "xxxhdpi", "drawable-xxxhdpi"));
		return list;
	}

	@Override
	public String getConverterName() {
		return "android-converter";
	}

	@Override
	public File createMainSubFolder(File destinationFolder, String targetImageFileName, Arguments arguments) {
		if (arguments.platform != EPlatform.ANDROID) {
			return MiscUtil.createAndCheckFolder(new File(destinationFolder, "android").getAbsolutePath(), arguments.dryRun);
		} else {
			return destinationFolder;
		}
	}

	@Override
	public File createFolderForOutputFile(File mainSubFolder, AndroidDensityDescriptor density, Dimension dimension, String targetFileName, Arguments arguments) {
		String folderName = (arguments.createMipMapInsteadOfDrawableDir ? density.folderName.replaceAll("drawable", "mipmap") : density.folderName);
		return MiscUtil.createAndCheckFolder(new File(mainSubFolder, folderName).getAbsolutePath(), arguments.dryRun);
	}

	@Override
	public String createDestinationFileNameWithoutExtension(AndroidDensityDescriptor density, Dimension dimension, String targetFileName, Arguments arguments) {
		return targetFileName;
	}

	@Override
	public void onPreExecute(File dstFolder, String targetFileName, List<AndroidDensityDescriptor> densityDescriptions, ImageType imageType, Arguments arguments) throws Exception {
		//nothing
	}

	@Override
	public void onPostExecute(Arguments arguments) {
		//nothing
	}
}