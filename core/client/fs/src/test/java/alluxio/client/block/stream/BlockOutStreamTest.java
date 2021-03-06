/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.client.block.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Tests {@link BlockOutStream}.
 */
public class BlockOutStreamTest {
  private static final int PACKET_SIZE = 128;

  @Test
  public void packetWriteException() throws Exception {
    PacketWriter writer = new FailingTestPacketWriter(ByteBuffer.allocate(PACKET_SIZE));
    BlockOutStream bos = new BlockOutStream(writer, PACKET_SIZE);
    try {
      bos.write(new byte[PACKET_SIZE]);
      Assert.fail("Expected write to throw an exception.");
    } catch (IOException e) {
      // Exception expected, continue.
    }
    // After an exception, we should still be able to cancel the stream.
    // Test succeeds if we do not throw an exception in cancel.
    bos.cancel();
  }
}
