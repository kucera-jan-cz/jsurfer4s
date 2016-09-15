package org.jsfr.jsurfer4s

import java.io.Reader

trait JsonMethods {
  def parse(in: Reader, listeners: SurferListener*): Unit
}
