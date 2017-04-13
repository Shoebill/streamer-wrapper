package net.gtaun.shoebill.streamer

/**
 * @author Marvin Haschker
 */
@AllOpen
class NativeNotFoundException(native: String) : Throwable("Could not find native function $native.")