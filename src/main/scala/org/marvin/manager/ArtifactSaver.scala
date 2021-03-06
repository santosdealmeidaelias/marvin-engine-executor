/*
 * Copyright [2017] [B2W Digital]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.marvin.manager

import akka.actor.Props
import org.marvin.model.{EngineMetadata, MarvinEExecutorException}

object ArtifactSaver {
  case class SaveToLocal(artifactName: String, protocol:String)
  case class SaveToRemote(artifactName: String, protocol:String)

  def build(metadata: EngineMetadata): Props = {
    metadata.artifactManagerType.toUpperCase match {
      case "FS" => return Props(new ArtifactFSSaver(metadata))
      case "HDFS" => return Props(new ArtifactHdfsSaver(metadata))
      case "S3" => return Props(new ArtifactS3Saver(metadata))
      case _ => throw new MarvinEExecutorException(s"Can not recognize ArtifactManagerType from EngineMetadata")
    }
  }
}
