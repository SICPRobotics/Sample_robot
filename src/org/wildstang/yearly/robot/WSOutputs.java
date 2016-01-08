package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.hardware.OutputConfig;
import org.wildstang.framework.io.outputs.OutputType;
import org.wildstang.hardware.crio.outputs.WSOutputType;
import org.wildstang.hardware.crio.outputs.config.WsVictorConfig;

public enum WSOutputs implements Outputs
{
   VICTOR_1_LEFT("Victor 1 Left",          WSOutputType.VICTOR,    new WsVictorConfig(0, 0.0), true),
   VICTOR_2_LEFT("Victor 2 Left",          WSOutputType.VICTOR,    new WsVictorConfig(1, 0.0), true),
   VICTOR_1_RIGHT("Victor 1 Right",          WSOutputType.VICTOR,    new WsVictorConfig(2, 0.0), true),
   VICTOR_2_RIGHT("Victor 2 Right",          WSOutputType.VICTOR,    new WsVictorConfig(3, 0.0), true);
   
   private String m_name;
   private OutputType m_type;
   private OutputConfig m_config;
   private boolean m_trackingState;

   WSOutputs(String p_name, OutputType p_type, OutputConfig p_config, boolean p_trackingState)
   {
      m_name = p_name;
      m_type = p_type;
      m_config = p_config;
      m_trackingState = p_trackingState;
   }
   
   
   @Override
   public String getName()
   {
      return m_name;
   }
   
   @Override
   public OutputType getType()
   {
      return m_type;
   }
   
   public OutputConfig getConfig()
   {
      return m_config;
   }

   public boolean isTrackingState()
   {
      return m_trackingState;
   }

}
