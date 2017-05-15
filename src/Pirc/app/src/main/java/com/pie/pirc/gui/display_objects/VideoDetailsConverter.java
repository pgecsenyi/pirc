package com.pie.pirc.gui.display_objects;

import com.pie.pirc.communication.data.VideoDetails;
import com.pie.pirc.communication.data.VideoFileDetails;
import com.pie.pirc.communication.data.VideoSubtitleDetails;

/**
 * Converts video details Data Transfer Object to Display Object.
 *
 * Created by pgecsenyi on 2015.06.14..
 */
public class VideoDetailsConverter
{
    public static VideoDetailsDo Convert(VideoDetails videoDetails)
    {
        VideoFileDetails[] fileDetails = videoDetails.getFiles();
        VideoFileDetailsDo[] fileDetailsDo = new VideoFileDetailsDo[fileDetails.length];
        for (int i = 0; i < fileDetails.length; i++)
        {
            fileDetailsDo[i] = new VideoFileDetailsDo(
                fileDetails[i].getId(),
                fileDetails[i].getLanguage(),
                fileDetails[i].getQuality());
        }

        VideoSubtitleDetails[] subtitleDetails = videoDetails.getSubtitles();
        VideoSubtitleDetailsDo[] subtitleDetailsDo = new VideoSubtitleDetailsDo[subtitleDetails.length];
        for (int i = 0; i < subtitleDetails.length; i++)
        {
            subtitleDetailsDo[i] = new VideoSubtitleDetailsDo(
                subtitleDetails[i].getId(),
                subtitleDetails[i].getIdVideo(),
                subtitleDetails[i].getLanguage());
        }

        return new VideoDetailsDo(videoDetails.getTitle(), fileDetailsDo, subtitleDetailsDo);
    }
}
