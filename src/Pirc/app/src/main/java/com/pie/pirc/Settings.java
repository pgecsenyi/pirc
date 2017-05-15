package com.pie.pirc;

import com.pie.pirc.communication.interfaces.AudioOutputs;

/**
 * Stores application settings.
 *
 * Created by pgecsenyi on 2015.06.27..
 */
public class Settings
{
    // Keys.
    public final static String AUDIO_PREFERRED_AUDIO_OUTPUT = "audio_preferred_audio_output";
    public final static String CONNECTION_ADDRESS = "connection_address";
    public final static String CONNECTION_PROTOCOL = "connection_protocol";
    public final static String GENERAL_UI_LANGUAGE = "general_ui_language";
    public final static String VIDEO_PREFERRED_AUDIO_OUTPUT = "video_preferred_audio_output";
    public final static String VIDEO_PREFERRED_LANGUAGE = "video_preferred_language";
    public final static String VIDEO_PREFERRED_SUBTITLE_LANGUAGE = "video_preferred_subtitle_language";

    // Default values.
    public final static String AUDIO_PREFERRED_AUDIO_OUTPUT_DEFAULT = AudioOutputs.DIGITAL;
    public final static String CONNECTION_ADDRESS_DEFAULT = "localhost";
    public final static String CONNECTION_PROTOCOL_DEFAULT = "HTTP (REST)";
    public final static String GENERAL_PREFERRED_AUDIO_OUTPUT_DEFAULT = AudioOutputs.DIGITAL;
    public final static String GENERAL_UI_LANGUAGE_DEFAULT = "English";
    public final static String VIDEO_PREFERRED_AUDIO_OUTPUT_DEFAULT = AudioOutputs.DIGITAL;
    public final static String VIDEO_PREFERRED_LANGUAGE_DEFAULT = "English";
    public final static String VIDEO_PREFERRED_SUBTITLE_LANGUAGE_DEFAULT = "None";
}
