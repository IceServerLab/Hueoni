package jp.iceserver.hueoni

class Hueoni : AbstractHueoni()
{
    companion object
    {
        lateinit var plugin: Hueoni
    }

    override fun onEnable()
    {
        plugin = this
    }
}