package org.wildstang.yearly.robot;

import org.wildstang.framework.core.Subsystems;
import org.wildstang.yearly.subsystems.Monitor;
import org.wildstang.yearly.subsystems.SixWheelDriveBase;

public enum WSSubsystems implements Subsystems
{
   MONITOR("Monitor", Monitor.class),
   DRIVE("Drive base", SixWheelDriveBase.class);

   private String m_name;
   private Class m_class;

   WSSubsystems(String p_name, Class p_class)
   {
      m_name = p_name;
      m_class = p_class;
   }

   @Override
   public String getName()
   {
      return m_name;
   }

   @Override
   public Class getSubsystemClass()
   {
      return m_class;
   }


}
