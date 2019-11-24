package com.example.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.Address;
import com.example.demo.model.local.Filial;
import com.example.demo.model.local.GPSCoordinates;

public class CityLocator {

  private static final Logger LOG = LoggerFactory.getLogger(CityLocator.class);

  private Map<CityAddress, GPSCoordinates> cityCenters;
  private BBox globalBox;

  public CityLocator(Collection<Filial> data) {
    Map<CityAddress, BBox> cityAreas = new HashMap<>();
    for (Filial filial: data) {
      if (filial.gps.isZero()) LOG.debug("Zero GPS coordinates for {} (id={})", filial.address, filial.id);
      else {
        CityAddress city = new CityAddress(filial.address);
        BBox area = cityAreas.get(city);
        if (area == null) cityAreas.put(city, new BBox(filial.gps));
        else area.include(filial.gps);
      }
    }
    cityCenters = new HashMap<>(cityAreas.size());
    globalBox = new BBox();
    cityAreas.forEach((address, area) -> {
      GPSCoordinates center = area.getCenter();
      globalBox.include(center);
      cityCenters.put(address, center);
    });
    globalBox.getCenter();
  }

  public CityAddress findClosest(GPSCoordinates location) {
    double min = Double.POSITIVE_INFINITY, hypot;
    CityAddress closest = null;
    for (Map.Entry<CityAddress, GPSCoordinates> entry: cityCenters.entrySet())
      if ((hypot = hypot(entry.getValue(), location)) < min) {
        min = hypot;
        closest = entry.getKey();
      }
    return closest;
  }

  private static double hypot(GPSCoordinates a, GPSCoordinates b) {
    return Math.hypot(a.longitude - b.longitude, a.latitude - b.latitude);
  }

  /**
   * Прямоугольная область в GPS координатах. Географическая "прямоугольность"
   * тем меньше, чем больше область покрыта. Никак не учитывает заворот по
   * долготе.
   */
  private static class BBox {
    public final GPSCoordinates min;
    public final GPSCoordinates max;

    public BBox() {
      min = new GPSCoordinates(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
      max = new GPSCoordinates(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public BBox(GPSCoordinates initial) {
      min = new GPSCoordinates(initial);
      max = new GPSCoordinates(initial);
    }

    public void include(GPSCoordinates point) {
      if (point.longitude < min.longitude) min.longitude = point.longitude;
      if (point.longitude > max.longitude) max.longitude = point.longitude;
      if (point.latitude < min.latitude) min.latitude = point.latitude;
      if (point.latitude > max.latitude) max.latitude = point.latitude;
    }

    public GPSCoordinates getCenter() {
      return new GPSCoordinates((min.longitude + max.longitude) / 2, (min.latitude + max.latitude) / 2);
    }
  }

  /**
   * Короткий адрес города для использования в качестве ключа в HashMap
   */
  public static final class CityAddress {
    public final String cityType;
    public final String city;

    public CityAddress(Address address) {
      this.cityType = Address.isEmpty(address.cityType) ? null : address.cityType.toLowerCase();
      this.city = Address.isEmpty(address.city) ? null : address.city.toLowerCase();
    }

    @Override
    public int hashCode() {
      return 31 * Objects.hashCode(city) + Objects.hashCode(cityType);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof CityAddress)) return false;
      CityAddress o = (CityAddress) obj;
      return Objects.equals(city, o.city) && Objects.equals(cityType, o.cityType);
    }

    @Override
    public String toString() {
      return cityType + ' ' + city;
    }
  }
}
