package com.jielong.core.service;

import java.io.File;
import java.io.InputStream;

public interface QRcodeService {
   InputStream getCodeStream(Integer id);
   File getQRcodeImage(String path);
}
