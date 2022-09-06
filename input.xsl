<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tns="http://dom.com.java.xml.parser/entity">
    <xsl:template match="/tns:Cars">
        <html>
            <head>
                <title>Cars</title>
                <style type="text/css">
                    table{
                    border: 2px solid green;
                    float: left;
                    margin-left:50px; }
                    td {border: .5px solid grey; padding: 5px;}
                </style>
            </head>
            <body>
                <title>Car</title>
                <xsl:apply-templates select="Car"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="Car">
        <table>
            <tr>
                <td>
                    Car:
                </td>
                <td>
                    <xsl:value-of select="id"/>
                </td>
            </tr>
            <tr>
                <td>
                    Model:
                </td>
                <td>
                    <xsl:value-of select="Model"/>
                </td>
            </tr>
            <tr>
                <td>
                    Brand:
                </td>
                <td>
                    <xsl:value-of select="@Brand"/>
                </td>
            </tr>
            <tr>
                <td>
                    Color:
                </td>
                <td>
                    <xsl:value-of select="Color"/>
                </td>
            </tr>
            <tr>
                <td>
                    Wheel:
                </td>
                <td>
                    <xsl:apply-templates select="Wheel"/>
                </td>
            </tr>
            <tr>
                <td>
                    Engine:
                </td>
                <td>
                    <xsl:apply-templates select="Engine"/>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="Wheel">

        <tr>
            <td>
                Position:
            </td>
            <td>
                <xsl:value-of select="Position"/>
            </td>
        </tr>
        <tr>
            <td>
                Tire:
            </td>
            <td>
                <xsl:value-of select="Tire"/>
            </td>
        </tr>
        <tr>
            <td>
                Diameter:
            </td>
            <td>
                <xsl:value-of select="Diameter"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Engene">
        <tr>
            <td>
                Power:
            </td>
            <td>
                <xsl:value-of select="Power"/>
            </td>
        </tr>
        <tr>
            <td>
                Fuel:
            </td>
            <td>
                <xsl:value-of select="Fuel"/>
            </td>
        </tr>
        <tr>
            <td>
                Run:
            </td>
            <td>
                <xsl:value-of select="Run"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>