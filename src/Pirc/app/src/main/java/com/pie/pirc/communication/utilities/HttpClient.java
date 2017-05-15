package com.pie.pirc.communication.utilities;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.pie.pirc.communication.exceptions.CommunicationException;

/**
 * Responsible for doing the REST communication. Sends requests to the specified address and returns with the response
 * as a string.
 *
 * Created by pgecsenyi on 2015.04.26..
 */

public class HttpClient
{
    /***************************************************************************************************************//**
     * Private fields.
     ******************************************************************************************************************/

    private final static int defaultTimeout = 5000;

    /***************************************************************************************************************//**
     * Public fields.
     ******************************************************************************************************************/

    public final static int DELETE = 1;

    public final static int GET = 2;

    public final static int POST = 3;

    public final static int PUT = 4;

    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    /**
     * Gets the content of the specified address with no parameters using GET method and the default timeout.
     *
     * @param address The address to connect to.
     */
    public static String getContent(String address) throws CommunicationException
    {
        return getContent(address, null, GET, defaultTimeout);
    }

    /**
     * Gets the content of the specified address with no parameters using the given method and the default timeout.
     *
     * @param address The address to connect to.
     * @param method HTTP request method.
     */
    public static String getContent(String address, int method) throws CommunicationException
    {
        return getContent(address, null, method, defaultTimeout);
    }

    /**
     * Gets the content of the specified address using GET method and the default timeout.
     *
     * @param address The address to connect to.
     * @param parameters HTTP request parameters.
     */
    public static String getContent(String address, Map<String, String> parameters) throws CommunicationException
    {
        return getContent(address, parameters, GET, defaultTimeout);
    }

    /**
     * Gets the content of the specified address using the default timeout.
     *
     * @param address The address to connect to.
     * @param parameters HTTP request parameters.
     * @param method HTTP request method.
     */
    public static String getContent(String address, Map<String, String> parameters, int method)
        throws CommunicationException
    {
        return getContent(address, parameters, method, defaultTimeout);
    }

    /**
     * Gets the content of the specified address.
     *
     * @param address The address to connect to.
     * @param parameters HTTP request parameters.
     * @param method HTTP request method.
     * @param timeout Time limit for the query.
     */
    public static String getContent(String address, Map<String, String> parameters, int method, int timeout)
        throws CommunicationException
    {
        HttpURLConnection connection = null;

        try
        {
            // Connect to the given address.
            if (method == GET)
                connection = initiateGetConnection(address, parameters, timeout);
            else
                connection = initiatePostPutDeleteConnection(address, parameters, method, timeout);

            if (connection == null)
            {
                Log.e("HttpClient", "Connecting to address \"" + address + "\"). Could not initiate connection.");
                return null;
            }

            connection.connect();

            // Get response status code.
            int status = connection.getResponseCode();

            // Process response.
            switch (status)
            {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null)
                        sb.append(line).append("\n");
                    br.close();
                    String result = sb.toString();
                    Log.d("HttpClient", "Response: " + result);
                    return result;
            }
        }
        catch (MalformedURLException ex)
        {
            Log.e("HttpClient", "Connecting to address \"" + address + "\") failed, wrong URL... " + ex.toString());
            throw new CommunicationException("Wrong URL.", ex);
        }
        catch (IOException ex)
        {
            Log.e("HttpClient", "Connecting to address \"" + address + "\") failed, I/O error... " + ex.toString());
            throw new CommunicationException("I/O error.", ex);
        }
        finally
        {
            if (connection != null)
            {
                try
                {
                    connection.disconnect();
                }
                catch (Exception ex)
                {
                    Log.e(
                        "HttpClient",
                        "Connecting to address \"" + address + "\") failed, unknown error... " + ex.toString());
                }
            }
        }

        return null;
    }

    /***************************************************************************************************************//**
     * Private methods.
     ******************************************************************************************************************/

    private static String getQueryJsonString(Map<String, String> parameters) throws UnsupportedEncodingException
    {
        if (parameters == null)
            return "";

        StringBuilder result = new StringBuilder();
        result.append("{");

        for (Map.Entry pair : parameters.entrySet())
        {
            result.append("\"" + URLEncoder.encode(pair.getKey().toString(), "UTF-8") + "\"");
            result.append(":");
            result.append("\"" + URLEncoder.encode(pair.getValue().toString(), "UTF-8") + "\"");
            result.append(",");
        }

        result.deleteCharAt(result.length() - 1);
        result.append("}");

        return result.toString();
    }

    private static String getQueryString(Map<String, String> parameters) throws UnsupportedEncodingException
    {
        if (parameters == null)
            return "";

        boolean isFirst = true;
        StringBuilder result = new StringBuilder();

        for (Map.Entry pair : parameters.entrySet())
        {
            if (isFirst)
                isFirst = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getKey().toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue().toString(), "UTF-8"));
        }

        if (!isFirst)
            result.insert(0, "?");

        return result.toString();
    }

    private static HttpURLConnection initiateGetConnection(String address, Map<String, String> parameters, int timeout)
        throws IOException, ProtocolException
    {
        if (address == null)
            return null;

        URL url = new URL(address + getQueryString(parameters));

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-length", "0");
        connection.setUseCaches(false);

        return connection;
    }

    private static HttpURLConnection initiatePostPutDeleteConnection(
        String address,
        Map<String, String> parameters,
        int method,
        int timeout)
        throws IOException, ProtocolException
    {
        if (address == null)
            return null;

        URL url = new URL(address);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(timeout);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setReadTimeout(timeout);
        if (method == DELETE)
            connection.setRequestMethod("DELETE");
        if (method == PUT)
            connection.setRequestMethod("PUT");
        else
            connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(true);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        String s = getQueryJsonString(parameters);
        Log.d("HttpClient", s);
        writer.write(getQueryJsonString(parameters));
        writer.flush();
        writer.close();
        os.close();

        return connection;
    }
}
